package com.triton.johnson.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "johnson.db";
    public static final int DATABASE_VERSION = 1;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(PART_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static final String PART_ID = "part_id";
    public static  final String PART_TABLE = "part_table";
    public static  final String _ID = "_id";
    public static  final String PART_NAME = "part_name";
    public static final String PART_NO = "part_no";
    public static final String PART_TYPE = "part_type";
    public static final String STATUS = "status";
    public static final String JOBID = "jobid";

    public static final String PART_QUERY = "CREATE TABLE "
            + PART_TABLE + " (" + PART_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PART_NAME
            + " TEXT , " + _ID
            + " TEXT , " + PART_NO
            + " TEXT , " + JOBID
            + " TEXT , " + PART_TYPE
            + " TEXT , " + STATUS
            + " TEXT );";
}
