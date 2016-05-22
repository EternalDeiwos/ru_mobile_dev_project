package com.example.g11f0364.graemesappfortestingstuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends BaseActivity
{

    ListView list;
    String[] web = {
            "BirdPix",
            "BOP",
            "DungBeetleMAP",
            "EchinoMAP",
            "FishMAP",
            "FrogMAP",
            "LacewingMAP"
    } ;
    Integer[] imageId = {
            R.drawable.birdpix_logo,
            R.drawable.bop_logo,
            R.drawable.dungbeetlemap_logo,
            R.drawable.echinomap_logo,
            R.drawable.fishmap_logo,
            R.drawable.frogmap_logo,
            R.drawable.lacewingmap_logo

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        custom_database_list adapter = new
                custom_database_list(HomeActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(HomeActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }
}
