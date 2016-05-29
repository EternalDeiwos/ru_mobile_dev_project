package com.github.prawncake.biomapapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.eternaldeiwos.biomapapp.model.Permission;
import com.github.eternaldeiwos.biomapapp.model.Project;
import com.github.eternaldeiwos.biomapapp.model.User;
import com.github.eternaldeiwos.biomapapp.rest.RestProject;
import com.github.eternaldeiwos.biomapapp.rest.RestUser;

import java.util.Map;

import com.github.eternaldeiwos.biomapapp.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nameField = (TextView) findViewById(R.id.nameField);
        aduField = (TextView) findViewById(R.id.aduField);
        addDBBtn = (Button) findViewById(R.id.button);

        //TODO: set what happens when a user clicks on a list item
    }

    public void getDetails()
    {
        //TODO: get the user's details and fill in their ADU number and Name
    }

    public void AddDatabaseButtonClick(View view)
    {
//        Intent intent = new Intent(this, SelectDBActivity.class);
//        startActivityForResult(intent, 2);

        RestUser.getUser(
                testUserADUNumber,
                testUserEmail,
                testUserPasswordEnc,
                new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User tmp = response.body();
                Log.d("USER", tmp.toString());
                Log.d("USER TEST", tmp.name.equals(testUserNameShouldEqual)
                        && tmp.surname.equals(testUserSurnameShouldEqual)
                        ? "passed"
                        : "failed"
                );

                nameField.setText(tmp.toString());
                aduField.setText(tmp.adu_number);

                RestUser.getPrivileges(tmp.token, new Callback<Permission>() {
                    @Override
                    public void onResponse(Call<Permission> call, Response<Permission> response) {
                        Permission permission = response.body();
                        Log.d("PERMISSION", permission.toString());
                    }

                    @Override
                    public void onFailure(Call<Permission> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Retrofit", t.getMessage());
            }
        });

        RestProject.getProjects(new Callback<Map<String, Project>>() {
            @Override
            public void onResponse(Call<Map<String, Project>> call, Response<Map<String, Project>> response) {
                for (String s : response.body().keySet()) {
                    Log.d("PROJECTS", s);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Project>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
