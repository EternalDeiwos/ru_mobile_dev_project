package com.github.prawncake.biomapapp;

import android.app.Activity;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.github.eternaldeiwos.biomapapp.LoginActivity;
import com.github.eternaldeiwos.biomapapp.Authenticator;
import com.github.eternaldeiwos.biomapapp.AuthenticatorActivity;
import com.github.eternaldeiwos.biomapapp.AuthenticatorService;
import com.github.eternaldeiwos.biomapapp.LocationProvider;
import com.github.eternaldeiwos.biomapapp.SelectLocationActivity;
import com.github.eternaldeiwos.biomapapp.model.Database;
import com.github.eternaldeiwos.biomapapp.model.Permission;
import com.github.eternaldeiwos.biomapapp.model.Project;
import com.github.eternaldeiwos.biomapapp.model.User;
import com.github.eternaldeiwos.biomapapp.rest.RestProject;
import com.github.eternaldeiwos.biomapapp.rest.RestUser;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.github.eternaldeiwos.biomapapp.R;
import com.orm.SugarContext;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    List<String> dbName;
    List <Integer> dbPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dbName = new ArrayList<String>();
        dbPicture = new ArrayList<Integer>();

        nameField = (TextView) findViewById(R.id.nameField);
        aduField = (TextView) findViewById(R.id.aduField);
        addDBBtn = (Button) findViewById(R.id.button);

        SugarContext.init(this);

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

                String dbName = data.getStringExtra("name");
                String uri = data.getStringExtra("uri");


            }
            if(resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(this, "No database added", Toast.LENGTH_LONG).show();
            }
        }
    }
}
