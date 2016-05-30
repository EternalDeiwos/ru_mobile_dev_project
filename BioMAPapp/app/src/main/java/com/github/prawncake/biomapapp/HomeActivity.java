package com.github.prawncake.biomapapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.github.eternaldeiwos.biomapapp.LoginActivity;
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

    private TextView nameField;
    private TextView aduField;
    private Button addDBBtn;

    ListView list;
    dbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        db = new dbHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SugarContext.init(this);

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



        nameField = (TextView) findViewById(R.id.nameField);
        aduField = (TextView) findViewById(R.id.aduField);
        addDBBtn = (Button) findViewById(R.id.button);

        final Button contextMenuButton = (Button)findViewById(R.id.button4);

        contextMenuButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                contextMenuButton.performLongClick();
            }
        }
        );

        registerForContextMenu(contextMenuButton);


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

    public void gregsLoginStuff()
    {

    }


//        RestUser.getUser(
//                testUserADUNumber,
//                testUserEmail,
//                testUserPasswordEnc,
//                new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User tmp = response.body();
//                Log.d("USER", tmp.toString());
//                Log.d("USER TEST", tmp.name.equals(testUserNameShouldEqual)
//                        && tmp.surname.equals(testUserSurnameShouldEqual)
//                        ? "passed"
//                        : "failed"
//                );
//
//                nameField.setText(tmp.toString());
//                aduField.setText(tmp.adu_number);
//
//                RestUser.getPrivileges(tmp.token, new Callback<Permission>() {
//                    @Override
//                    public void onResponse(Call<Permission> call, Response<Permission> response) {
//                        Permission permission = response.body();
//                        Log.d("PERMISSION", permission.toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<Permission> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.d("Retrofit", t.getMessage());
//            }
//        });
//
//        RestProject.getProjects(new Callback<Map<String, Project>>() {
//            @Override
//            public void onResponse(Call<Map<String, Project>> call, Response<Map<String, Project>> response) {
//                for (String s : response.body().keySet()) {
//                    Log.d("PROJECTS", s);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Map<String, Project>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

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
