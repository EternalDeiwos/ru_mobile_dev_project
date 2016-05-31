package com.github.prawncake.biomapapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.github.eternaldeiwos.biomapapp.LoginActivity;
import com.github.eternaldeiwos.biomapapp.Authenticator;
import com.github.eternaldeiwos.biomapapp.AuthenticatorActivity;
import com.github.eternaldeiwos.biomapapp.helper.API;
import com.github.eternaldeiwos.biomapapp.helper.dbHelper;

import com.github.eternaldeiwos.biomapapp.R;
import com.github.eternaldeiwos.biomapapp.model.Database;
import com.orm.SugarContext;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private static final String testUserADUNumber = "90000";
    private static final String testUserEmail = "bobbytwoshoes@anemailserver.com";
    private static final String testUserPasswordEnc = "a9c4cef5735770e657b7c25b9dcb807b";
    private static final String testUserNameShouldEqual = "Bobs";
    private static final String testUserSurnameShouldEqual = "YourUncle";

    private Button addDBBtn;

    ListView list;
    dbHelper db;
    int list_idx;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        db = new dbHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SugarContext.init(this);
        first_time_check();

        list = (ListView) findViewById(R.id.listView);



        Database[] projectDatabases = new Database[db.getEntryCount()];
        ArrayList<Database> projectDatabasesList = db.getUserDataProjects();

        projectDatabases = projectDatabasesList.toArray(projectDatabases);

        String []projectNames = new String [db.getEntryCount()];
        Uri[] projectUris = new Uri [db.getEntryCount()];

        for (int i =0; i<db.getEntryCount(); i++)
        {
            projectNames[i] = projectDatabases[i].name;
            projectUris[i] =  Uri.parse(API.ADU_VMUS_URL + "images/" + projectDatabases[i].project + "_logo.png");
        }

        list = (ListView) findViewById(R.id.listView);
        custom_database_list projects = new custom_database_list(this, projectNames, projectUris);
        list.setAdapter(projects);
        addDBBtn = (Button) findViewById(R.id.button);

        registerForContextMenu(list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                list.performLongClick();
                list_idx = position;
                //Toast.makeText(HomeActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        }
        );

        //TODO: set what happens when a user clicks on a list item
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo)
    {
        getMenuInflater().inflate(R.menu.records_menu, menu);
    }

    @Override
    public boolean onContextItemSelected (MenuItem item)
    {
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.newRecord_menu:
                intent = new Intent(this, CreateRecordActivity.class);
//                Log.d("ITEM", list.getItemAtPosition(list_idx).toString());
                intent.putExtra("project_acronym", list.getItemAtPosition(list_idx).toString());
                startActivityForResult(intent,NO_HANDLER_NEEDED);
                return true;
            case R.id.viewRecord_menu:
                intent = new Intent(this, ViewRecordsActivity.class);
                startActivityForResult(intent,NO_HANDLER_NEEDED);
                return true;
            case R.id.remove_menu:
                //TODO
                return true;
            default:
                return false;
        }
    }

    public void first_time_check()
    {
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccountsByType(AuthenticatorActivity.ACCOUNT_TYPE);
        if (accounts == null || accounts.length == 0) {
            am.addAccount(
                    AuthenticatorActivity.ACCOUNT_TYPE,
                    AuthenticatorActivity.ARG_AUTH_TYPE,
                    null,
                    null,
                    this,
                    new AccountManagerCallback<Bundle>() {
                        @Override
                        public void run(AccountManagerFuture<Bundle> future) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    },
                    null
            );
        } else {
            TextView nameField;
            TextView aduField;
            nameField = (TextView) findViewById(R.id.nameField);
            aduField = (TextView) findViewById(R.id.aduField);
            nameField.setText(am.getUserData(accounts[0], Authenticator.KEY_USER_NAME) + " " +
                    am.getUserData(accounts[0], Authenticator.KEY_USER_SURNAME));
            aduField.setText(am.getUserData(accounts[0], Authenticator.KEY_ADU_NUMBER));
        }
    }

    public void AddDatabaseButtonClick(View view)
    {
        Intent intent = new Intent(this, SelectDBActivity.class);
        startActivityForResult(intent, ADD_NEW_DATABASE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == ADD_NEW_DATABASE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Toast.makeText(this, "Database added", Toast.LENGTH_LONG).show();

                String name = data.getStringExtra("name");
                String project = data.getStringExtra("db_name");
                db.addProject(new Database(name,project));

                Database[] projectDatabases = new Database[db.getEntryCount()];
                ArrayList<Database> projectDatabasesList = db.getUserDataProjects();

                projectDatabases = projectDatabasesList.toArray(projectDatabases);

                String []projectNames = new String [db.getEntryCount()];
                Uri[] projectUris = new Uri [db.getEntryCount()];

                for (int i =0; i<db.getEntryCount(); i++)
                {
                    projectNames[i] = projectDatabases[i].name;
                    projectUris[i] =  Uri.parse(API.ADU_VMUS_URL + "images/" + projectDatabases[i].project + "_logo.png");
                }

                list = (ListView) findViewById(R.id.listView);
                custom_database_list projects = new custom_database_list(this, projectNames, projectUris);
                list.setAdapter(projects);

            }
            if(resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(this, "No database added", Toast.LENGTH_LONG).show();
            }
        }
    }
}
