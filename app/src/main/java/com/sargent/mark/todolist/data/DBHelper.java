package com.sargent.mark.todolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mark on 7/4/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "items.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //added new columns finished and category
        //added IF NOT EXISTS for more safety
        String queryString = "CREATE TABLE IF NOT EXISTS " + Contract.TABLE_TODO.TABLE_NAME + " (" +
                Contract.TABLE_TODO._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE + " DATE, " +
                Contract.TABLE_TODO.COLUMN_NAME_FINISHED + " INTEGER DEFAULT 0, " +
                Contract.TABLE_TODO.COLUMN_NAME_CATEGORY + " TEXT NOT NULL" + "); ";
        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //added upgrade functionality to deal with upgrading from previous version of db
        db.execSQL("DROP TABLE IF EXISTS " + Contract.TABLE_TODO.TABLE_NAME + ";");
        //redo check if table exists
        //added new columns finished and category
        String queryString = "CREATE TABLE IF NOT EXISTS " + Contract.TABLE_TODO.TABLE_NAME + " (" +
                Contract.TABLE_TODO._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL, " +
                Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE + " DATE, " +
                Contract.TABLE_TODO.COLUMN_NAME_FINISHED + " INTEGER DEFAULT 0, " +
                Contract.TABLE_TODO.COLUMN_NAME_CATEGORY + " TEXT NOT NULL" + "); ";
        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }
}
