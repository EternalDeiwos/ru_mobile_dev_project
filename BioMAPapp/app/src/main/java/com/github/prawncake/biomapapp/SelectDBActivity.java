package com.github.prawncake.biomapapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.eternaldeiwos.biomapapp.R;
import com.github.eternaldeiwos.biomapapp.model.Project;
import com.github.eternaldeiwos.biomapapp.rest.RestProject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDBActivity extends BaseActivity
{
    String [] projectsNameString;
    Uri [] projectImageUri;
    String [] projectsString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_db);

        RestProject.getProjects(new Callback<Map<String, Project>>()
        {
            @Override
            public void onResponse(Call<Map<String, Project>> call, Response<Map<String, Project>> response)
            {
                ListView list;
                Map<String,Project> allProjects =  response.body();

                projectsString = new String[allProjects.keySet().size()];
                projectsNameString = new String[allProjects.keySet().size()];
                projectImageUri = new Uri[allProjects.keySet().size()];

                allProjects.keySet().toArray(projectsString);

                for (int i =0; i<allProjects.keySet().size()-1; i++)
                {
                    projectImageUri[i] = allProjects.get(projectsString[i]).getImageURI();
                    projectsNameString[i] = allProjects.get(projectsString[i]).getAcronym();
                }
                custom_database_list adapter = new custom_database_list(SelectDBActivity.this, projectsNameString, projectImageUri);

                list=(ListView)findViewById(R.id.databases);
                list.setAdapter(adapter);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Toast.makeText(SelectDBActivity.this, "You Clicked at " +position, Toast.LENGTH_SHORT).show();
                        Intent newProject = new Intent();
                        newProject.putExtra("name",projectsNameString[position]);
                        newProject.putExtra("db_name", projectsString[position]);
                        setResult(Activity.RESULT_OK, newProject);
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call<Map<String, Project>> call, Throwable t)
            {
                t.printStackTrace();
            }
        });
    }
}
