package eternaldeiwos.github.com.biomapapp;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
        switch (item.getItemId())
        {
            case R.id.settings_menu:
                Toast.makeText(this,"Settings selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.help_menu:
                Toast.makeText(this,"Help selected",Toast.LENGTH_SHORT).show();
            case R.id.logout_menu:
                Toast.makeText(this,"Logout selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.quit_menu:
                Toast.makeText(this,"Quit selected",Toast.LENGTH_SHORT).show();
            default:
                return false;
        }
    }
}
