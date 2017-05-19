/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetDbHelper;


/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        displayDatabaseInfo();

    }

    private void displayDatabaseInfo() {
        PetDbHelper petDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = petDbHelper.getReadableDatabase();

        //Cursor cursor = db.rawQuery("select * from " + PetContract.PetEntry.TABLE_NAME, null);

        String selection = PetContract.PetEntry.COLUMN_PET_GENDER + "=?";
        String[] selectionArgs =
                new String[]
                {String.valueOf("Male")};


        Cursor cursor = db.query(PetContract.PetEntry.TABLE_NAME,
                null, null,
                null, null,
                null, null);
        try {

            System.out.println("Records in the Databse " + cursor.getCount());


            while (cursor.moveToNext()) {
                System.out.println(cursor.getString(1) + " : " + cursor.getString(2));
            }
        } finally {
            cursor.close();
        }

//        Code to insert values
//        ContentValues values = new ContentValues();
//        values.put(PetEntry.COLUMN_PET_NAME, "Garfield");
//        values.put(PetEntry.COLUMN_PET_BREED, "Tabby");
//        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
//        values.put(PetEntry.COLUMN_PET_WEIGHT, 7);
//        db.insert(PetEntry.TABLE_NAME, null, values);
    }


    /**
     * Helper method to insert hardcoded pet data into the database.
     * For debugging purposes only.
     */
    private void insertPet() {
        // Gets the database in write mode
        PetDbHelper petDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = petDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetContract.PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetContract.PetEntry.COLUMN_PET_GENDER, PetContract.PetEntry.GENDER_MALE);
        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, 7);

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.

        long newRowId = db.insert(PetContract.PetEntry.TABLE_NAME, null, values);

        System.out.println("record inserted  with newRowId " + newRowId);

    }


    /**
     * Helper method to insert hardcoded pet data into the database.
     * For debugging purposes only.
     */
    private void deleteAllPet() {
        // Gets the database in write mode
        PetDbHelper petDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = petDbHelper.getWritableDatabase();

        String selection = null; //FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = null; //{"MyTitle"};
        db.delete(PetContract.PetEntry.TABLE_NAME, selection, selectionArgs);

    }

    /**
     * Helper method to insert hardcoded pet data into the database.
     * For debugging purposes only.
     */
    private void updatePet() {
        // Gets the database in write mode
        PetDbHelper petDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = petDbHelper.getWritableDatabase();

//        // New value for one column
//        ContentValues values = new ContentValues();
//        values.put(FeedEntry.COLUMN_NAME_TITLE, title);
//
//// Which row to update, based on the title
//        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
//        String[] selectionArgs = {"MyTitle"};
//
//        int count = db.update(
//                FeedReaderDbHelper.FeedEntry.TABLE_NAME,
//                values,
//                selection,
//                selectionArgs);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                deleteAllPet();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
