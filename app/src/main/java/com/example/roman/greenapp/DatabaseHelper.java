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

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //Create table "disaster"
        db.execSQL("CREATE TABLE disaster(" + "_id integer primary key autoincrement, title text, solved integer, date integer, latitude real, longitude real, altitude real");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public long insertDisaster(Disaster disaster){
        ContentValues cv = new ContentValues();
        cv.put("title",disaster.getTitle());
        cv.put("solved",disaster.isSolved());
        cv.put("date", disaster.getDate().getTime());
        return getWritableDatabase().insert("disaster",null,cv);
    }
}
