package com.example.roman.greenapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Roman on 8/22/2015.
 */
public class DisasterPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Disaster> mDisasters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mDisasters = DisasterStorage.get(this).getDisasters();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Disaster disaster = mDisasters.get(position);

                return DisasterFragment.newInstance(disaster.getId());
            }

            @Override
            public int getCount() {
                return mDisasters.size();
            }
        });

        UUID disasterID = (UUID) getIntent().getSerializableExtra(DisasterFragment.EXTRA_DISASTER_ID);
        setTitle(DisasterStorage.get(this).getDisaster(disasterID).getTitle());
        for (int i = 0; i < mDisasters.size(); i++) {
            if (mDisasters.get(i).getId().equals(disasterID)) {
                mViewPager.setCurrentItem(i);

                break;
            }
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Disaster disaster = mDisasters.get(position);
                if (disaster.getTitle() != null) {
                    setTitle(disaster.getTitle());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}