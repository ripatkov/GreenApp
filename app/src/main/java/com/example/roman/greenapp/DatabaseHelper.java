package com.example.roman.greenapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Roman on 29-01-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "disaster.sqlite";
    private static final int DB_VERSION = 1;
    private static final String DISASTERS_TABLE_NAME = "disasters";
    private static final String DISASTERS_TABLE_CREATE ="CREATE TABLE " + DISASTERS_TABLE_NAME+"(disaster_id text primary key, title text, solved integer, date integer, latitude real, longitude real, altitude real);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //Create table "disasters"
        db.execSQL(DISASTERS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public long insertDisaster(Disaster disaster){
        ContentValues cv = new ContentValues();
        cv.put("title",disaster.getTitle());
        cv.put("solved",disaster.isSolved());
        cv.put("date", disaster.getDate().getTime());
        return getWritableDatabase().insert(DISASTERS_TABLE_NAME,null,cv);
    }
}
