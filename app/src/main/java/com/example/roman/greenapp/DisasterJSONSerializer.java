package com.example.roman.greenapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Roman on 16-01-2016.
 */
public class DisasterJSONSerializer {

    private Context mContext;
    private String mFilename;
    public DisasterJSONSerializer(Context c, String f){
        mContext = c;
        mFilename = f;
    }

    public void saveDisasters(ArrayList<Disaster> mDisasters) throws JSONException, IOException
    {
        JSONArray array = new JSONArray();
        for (Disaster d: mDisasters){
            array.put(d.toJSON());
        }
        Writer writer =  null;
        try{
            OutputStream out = mContext
                    .openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }
        finally {
            if (writer!= null)
                writer.close();
        }
    }

}
