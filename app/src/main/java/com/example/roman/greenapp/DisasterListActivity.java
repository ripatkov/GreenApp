package com.example.roman.greenapp;

import android.support.v4.app.Fragment;
import android.view.Menu;

/**
 * Created by Roman on 28.7.2015.
 */
public class DisasterListActivity extends AbstractActivity {
    @Override
    protected Fragment createFragment() {
        return new DisasterListFragment();
    }


/*
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
*/

}