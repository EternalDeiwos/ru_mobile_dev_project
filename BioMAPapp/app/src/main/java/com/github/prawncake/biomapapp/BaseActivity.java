package com.github.prawncake.biomapapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.eternaldeiwos.biomapapp.LoginActivity;

import java.io.IOException;

import com.github.eternaldeiwos.biomapapp.R;

//This class acts as a base class to be included in any activity so that the menu items are all the same across all classes that inherit this.
public class BaseActivity extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.settings_menu:
                intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent,1);
                return true;
            case R.id.help_menu:
                intent = new Intent(this, HelpActivity.class);
                startActivityForResult(intent,2);
                return true;
            case R.id.logout_menu:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                //TODO: Changes saved
                Toast.makeText(this, "Changes to settings saved", Toast.LENGTH_LONG).show();
            }
            if(resultCode == Activity.RESULT_CANCELED)
            {
                //TODO: Changes cancelled
                Toast.makeText(this, "Changes to settings cancelled", Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == 2)
        {
            //TODO: Dont really think anything needs to happen here, returning from help screen
        }
    }
}
