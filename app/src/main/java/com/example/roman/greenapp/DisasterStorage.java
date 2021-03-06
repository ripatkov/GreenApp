package com.example.roman.greenapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Roman on 27.7.2015.
 */
public class DisasterStorage {
    private static final String TAG = "DisasterStorage";
    private static final String FILENAME = "disasters.json";
    private DisasterJSONSerializer mSerializer;


    private static DisasterStorage sDisasterStorage;
    private Context mAppContext;
    private ArrayList<Disaster> mDisasters;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDB;

    private DisasterStorage(Context appContext){
        mAppContext=appContext;
        mDisasters=new ArrayList<Disaster>();
        mDatabaseHelper = new DatabaseHelper(mAppContext);
        mDB = mDatabaseHelper.getWritableDatabase();

    }
    public static DisasterStorage get(Context c){
        if (sDisasterStorage==null){
            sDisasterStorage=new DisasterStorage(c.getApplicationContext());
        }
        return sDisasterStorage;
    }

    public ArrayList<Disaster> getDisasters(){
        return mDisasters;
    }

    public Disaster getDisaster(UUID id){
        for (Disaster d:mDisasters) {
            if (d.getId().equals(id))
                return d;
        }
        return null;
    }

    public boolean saveDisasters()
    {
        try {
            mSerializer.saveDisasters(mDisasters);
            Log.d(TAG, "disasters saved to file");
            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "Error saving disasters: ", e);
            return false;
        }
    }

    public void addDisaster(Disaster d) {
        mDisasters.add(d);
        mDatabaseHelper.insertDisaster(d);

    }
    public void deleteDisaster(Disaster d) {
        mDisasters.remove(d);
    }
}

