package com.example.medicinesv2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "MEDICINES.DB";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_TABLE = "MEDICINES";
    static final String MED_ID = "ID";
    static final String MED_NAME = "med_name";
    static final String MED_DATE_OF_MANUFACTURE = "med_date_of_manufacture";
    static final String MED_BEFORE_DATE = "med_before_date";
    static final String MED_STORAGE = "med_storage";
    static final String MED_DESCRIPTION = "med_description";

    private static final String CREATE_DB_QUERY = "CREATE TABLE " + DATABASE_TABLE + " ( "
            + MED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MED_NAME + " TEXT NOT NULL, "
            + MED_DATE_OF_MANUFACTURE + " TEXT NOT NULL," + MED_BEFORE_DATE + " TEXT NOT NULL,"
            + MED_STORAGE + " TEXT NOT NULL," + MED_DESCRIPTION + " );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}