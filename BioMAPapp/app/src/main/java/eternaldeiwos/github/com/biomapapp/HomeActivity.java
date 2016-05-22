package eternaldeiwos.github.com.biomapapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //TODO: set what happens when a user clicks on a list item
    }

    public void getDetails()
    {
        //TODO: get the user's details and fill in their ADU number and Name
    }

    public void AddDatabaseButtonClick(View view)
    {
        //TODO: Open Database screen
    }
}
