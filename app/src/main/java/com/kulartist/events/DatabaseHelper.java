package com.kulartist.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "events";
    public static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "meetups";
    public static final String COL_1 = "EVENTID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "LOCATION";
    public static final String COL_4 = "STARTTIME";
    public static final String COL_5 = "ENDTIME";
    public static final String COL_6 = "USERID";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "  + TABLE_NAME + " " +
                "(EVENTID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME VARCHAR(255), " +
                "LOCATION VARCHAR(255), " +
                "STARTTIME VARCHAR(255), " +
                "ENDTIME VARCHAR(255), " +
                "USERID NUMBER);";

       // String sql1="CREATE TABLE TABLE_NAME (EVENTID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(255), LOCATION VARCHAR(255), STARTTIME VARCHAR(255), ENDTIME VARCHAR(255), USERID INTEGER);";


        db.execSQL(sql);
    }

    public long addEvent(String name, String location, String sTime, String eTime, int uId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NAME", name);
        cv.put("LOCATION", location);
        cv.put("STARTTIME", sTime);
        cv.put("ENDTIME", eTime);
        cv.put("USERID", uId);
        long insert = db.insert(TABLE_NAME, null, cv);
        db.close();

        return insert;
    }

    public int updateEvent(String event_id, String name, String location, String sTime, String eTime, int uId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_1, event_id);
        cv.put(COL_2, name);
        cv.put(COL_3, location);
        cv.put(COL_4, sTime);
        cv.put(COL_5, eTime);
        cv.put(COL_6, uId);
        int update = db.update(TABLE_NAME, cv, "EVENTID = ?", new String[] {event_id});
        db.close();

        return update;
    }

    public int deleteEvent(String id) {
        SQLiteDatabase db = getWritableDatabase();
        int delete = db.delete(TABLE_NAME, "EVENTID = ?", new String[] {id});
        db.close();

        return delete;
    }

    public Cursor viewAllProducts(int USERID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE USERID="+USERID, null);

        return c;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }
}