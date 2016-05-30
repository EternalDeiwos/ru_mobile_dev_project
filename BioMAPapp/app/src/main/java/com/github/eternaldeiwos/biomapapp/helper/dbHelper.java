package com.github.eternaldeiwos.biomapapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.eternaldeiwos.biomapapp.model.Database;

import java.util.ArrayList;

/**
 * Created by g11f0364 on 2016-05-17.
 */
public class dbHelper extends SQLiteOpenHelper
{
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "userStoredProjects";

    // Contacts table name
    private static final String TABLE_PROJECTS = "projects";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PROJECT_NAME = "project_name";
    private static final String KEY_PROJECT_SHORTNAME = "project_short_name";


    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PROJECTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PROJECT_NAME + " TEXT,"
                + KEY_PROJECT_SHORTNAME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addProject(Database entry)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROJECT_NAME, entry.toString());
        values.put(KEY_PROJECT_SHORTNAME, entry.getProject());

        // Inserting Row
        db.insert(TABLE_PROJECTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public ArrayList<Database> getUserDataProjects()
    {
        ArrayList<Database> userProjectList = new ArrayList<Database>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PROJECTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                Database entry = new Database((cursor.getString(1)),(cursor.getString(2)));

                userProjectList.add(entry);
            } while (cursor.moveToNext());
        }

        // return contact list
        return userProjectList;
    }

    public int getEntryCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_PROJECTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();

        // return count

    }
}
