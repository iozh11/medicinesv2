package com.example.medicinesv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context cxt) {
        context = cxt;
    }

    public DatabaseManager open() throws Exception {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (database != null) {
            database.close();
        }
        dbHelper.close();
    }

    public void insert(String medname, String meddateofman, String medbeforedate, String medstorage, String meddescription) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MED_NAME, medname);
        contentValues.put(DatabaseHelper.MED_DATE_OF_MANUFACTURE, meddateofman);
        contentValues.put(DatabaseHelper.MED_BEFORE_DATE, medbeforedate);
        contentValues.put(DatabaseHelper.MED_STORAGE, medstorage);
        contentValues.put(DatabaseHelper.MED_DESCRIPTION, meddescription);
        database.insert(DatabaseHelper.DATABASE_TABLE, null, contentValues);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper.MED_ID, DatabaseHelper.MED_NAME, DatabaseHelper.MED_DATE_OF_MANUFACTURE,
                DatabaseHelper.MED_BEFORE_DATE, DatabaseHelper.MED_STORAGE, DatabaseHelper.MED_DESCRIPTION};
        return database.query(DatabaseHelper.DATABASE_TABLE, columns, null, null, null, null, null);
    }

    public int update(long _id, String medname, String meddateofman, String medbeforedate, String medstorage, String meddescription) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MED_NAME, medname);
        contentValues.put(DatabaseHelper.MED_DATE_OF_MANUFACTURE, meddateofman);
        contentValues.put(DatabaseHelper.MED_BEFORE_DATE, medbeforedate);
        contentValues.put(DatabaseHelper.MED_STORAGE, medstorage);
        contentValues.put(DatabaseHelper.MED_DESCRIPTION,meddescription);
        return database.update(DatabaseHelper.DATABASE_TABLE, contentValues, DatabaseHelper.MED_ID + "=" + _id, null);
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.DATABASE_TABLE, DatabaseHelper.MED_ID + "=" + _id, null);
    }
}

