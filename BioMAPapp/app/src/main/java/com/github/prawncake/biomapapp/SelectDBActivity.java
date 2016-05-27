package com.github.prawncake.biomapapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import eternaldeiwos.github.com.biomapapp.R;

public class SelectDBActivity extends BaseActivity
{

    ListView list;
    String[] web = {
            "BirdPix",
            "BOP",
            "DungBeetleMAP",
            "EchinoMAP",
            "FishMAP",
            "FrogMAP",
            "LacewingMAP",
            "LepiMAP",
            "MushroomMAP",
            "OdonataMAP",
            "OrchidMAP",
            "PHOWN",
            "ReptileMAP",
            "ScorpionMAP",
            "SpiderMAP",
            "TreeMAP",
            "Vimma"
    } ;
    Integer[] imageId = {
            R.drawable.birdpix_logo,
            R.drawable.bop_logo,
            R.drawable.dungbeetlemap_logo,
            R.drawable.echinomap_logo,
            R.drawable.fishmap_logo,
            R.drawable.frogmap_logo,
            R.drawable.lacewingmap_logo,
            R.drawable.lepimap_logo,
            R.drawable.mushroommap_logo,
            R.drawable.odonata_logo,
            R.drawable.orchidmap_logo,
            R.drawable.phown_logo,
            R.drawable.reptilemap_logo,
            R.drawable.scorpionmap_logo,
            R.drawable.spidermap_logo,
            R.drawable.treemap_logo,
            R.drawable.vimma_logo
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_db);

        custom_database_list adapter = new
                custom_database_list(SelectDBActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.databases);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(SelectDBActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

    }
}
