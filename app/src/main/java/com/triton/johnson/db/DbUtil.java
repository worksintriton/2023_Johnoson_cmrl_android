package com.triton.johnson.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbUtil {

    static SQLiteDatabase db;
    Context context;
    ContentValues values;
    DbHelper dbHelper;

    public DbUtil(Context context) {
        this.context = context;
        values = new ContentValues();
    }

    public SQLiteDatabase open() {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
        }
        db = dbHelper.getWritableDatabase();
        return db;
    }

    public void close() {
        if (dbHelper != null) {
            db.close();
        }
    }

    public static final String[] PART_FIELD = {
            DbHelper.PART_ID,
            DbHelper.PART_NAME,
            DbHelper.PART_NO,
            DbHelper._ID,
            DbHelper.JOBID,
            DbHelper.PART_TYPE,
            DbHelper.STATUS,

    };

    public long addPart(String str_partid, String str_partname, String str_partno, String str_parttype, String jobid) {

        values.clear();
        values.put(DbHelper._ID,str_partid);
        values.put(DbHelper.PART_NAME,str_partname);
        values.put(DbHelper.PART_NO,str_partno);
        values.put(DbHelper.PART_TYPE,str_parttype);
        values.put(DbHelper.JOBID,jobid);
        return db.insert(DbHelper.PART_TABLE,null,values);

    }

    public Cursor getPart(String jobid) {
        return db.query(DbHelper.PART_TABLE,PART_FIELD,DbHelper.JOBID + "= '" + jobid + "'",
                null,null,null,null);
    }


    public boolean hasPart(String jobid, String str_partid) {
        String abc = DbHelper.JOBID + "= '" + jobid + "'" + "AND " + DbHelper._ID+ "= '" + str_partid + "'";
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbHelper.PART_TABLE + " WHERE " + abc , null);
        if (cursor.getCount()<= 0 ){
            cursor.close();
            return false;
        }
        cursor.close();
        return  true;
    }

    public int deletePart(String jobid, String str_partid) {
        return db.delete(DbHelper.PART_TABLE,DbHelper.JOBID + "= '" + jobid + "'"
                + "AND " + DbHelper._ID + "= '" + str_partid + "'",null) ;
    }

    public void reportdelete(String job_id) {
        db.delete(DbHelper.PART_TABLE, DbHelper.JOBID + "= '" + job_id + "'", null);

    }

    public void deleteparttable() {
        db.delete(DbHelper.PART_TABLE, null, null);
    }

    public Cursor getPartall() {
        return db.query(DbHelper.PART_TABLE,PART_FIELD,null,
                null,null,null,null);
    }
}
