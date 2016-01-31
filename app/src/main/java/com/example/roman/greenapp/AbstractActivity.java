package com.example.roman.greenapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


/**
 * Created by Roman on 28.7.2015.
 */
public abstract class AbstractActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment=createFragment();
            fm.beginTransaction()
                    .add(R.id.container,fragment).commit();
        }
    }
}
