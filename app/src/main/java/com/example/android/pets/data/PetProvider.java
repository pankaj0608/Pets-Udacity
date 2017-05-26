package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by pankaj on 5/26/2017.
 */
public class PetProvider extends ContentProvider {

    /**
     * URI matcher code for the content URI for the pets table
     */
    private static final int PETS = 100;

    /**
     * URI matcher code for the content URI for a single pet in the pets table
     */
    private static final int PET_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // TODO: Add 2 content URIs to URI matcher

        sUriMatcher.addURI("content", "pets", PETS);
        sUriMatcher.addURI("content", "pets/#", PET_ID);

    }

    /**
     * Database helper object
     */
    private PetDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new PetDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor = null;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        switch (match) {
            case PETS:
                System.out.println("I am in Pets " + uri);

                cursor = mDbHelper.getReadableDatabase().
                        query(PetContract.PetEntry.TABLE_NAME,
                        null, null,
                        null, null,
                        null, null);
                break;

            case PET_ID:
                System.out.println("I am in Pets Id " + uri);

                String selection = PetContract.PetEntry._ID + "=?";

                String[] selectionArgs =
                        new String[]
                                {String.valueOf(ContentUris.parseId(uri))};

                cursor = mDbHelper.getReadableDatabase().
                        query(PetContract.PetEntry.TABLE_NAME,
                                null, selection,
                                selectionArgs, null,
                                null, null);

                break;
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}