package com.example.roman.greenapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Roman on 27.7.2015.
 */
public class DisasterListFragment extends ListFragment {
    private ArrayList<Disaster> mDisasters;
    private static final String TAG="DisasterListFragment";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //getActivity().setTitle(R.string.disasters_title);
        mDisasters=DisasterStorage.get(getActivity()).getDisasters();
        /*ArrayAdapter<Disaster> adapter = new ArrayAdapter<Disaster>(getActivity(),android.R.layout.simple_list_item_1,mDisasters);
        setListAdapter(adapter);*/
        DisasterAdapter adapter = new DisasterAdapter(mDisasters);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l,View v,int position, long id){
        //Get Disaster object from adapter
        Disaster d=((DisasterAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, d.getTitle() + "was clicked");

        //launching DisasterPagerActivity (detailed info)
        Intent i = new Intent(getActivity(),DisasterPagerActivity.class);
        i.putExtra(DisasterFragment.EXTRA_DISASTER_ID, d.getId());
        startActivity(i);
    }


    @Override
    public void onResume(){
        super.onResume();
        ((DisasterAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_disaster:
                Disaster d = new Disaster();
                DisasterStorage.get(getActivity()).addDisaster(d);
                //mDisasters.add(d);
                Intent i = new Intent(getActivity(), DisasterPagerActivity.class);
                i.putExtra(DisasterFragment.EXTRA_DISASTER_ID, d.getId());
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private class DisasterAdapter extends ArrayAdapter<Disaster> {
        public DisasterAdapter(ArrayList<Disaster> disasters){
            super(getActivity(),0,disasters);
        }
        @Override
        public View getView(int position,View convertView,ViewGroup parent){
            if(convertView==null){
                convertView=getActivity().getLayoutInflater().inflate(R.layout.list_item_disaster,null);
            }
            Disaster d = getItem(position);
            TextView titleTextView=(TextView)convertView.findViewById(R.id.disaster_list_item_titleTextView);
            titleTextView.setText(d.getTitle());
            TextView dateTextView=(TextView)convertView.findViewById(R.id.disaster_list_item_dateTextView);
            dateTextView.setText(d.getDate().toString());
            CheckBox solvedCheckBox=(CheckBox)convertView.findViewById(R.id.disaster_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(d.isSolved());
            return convertView;
        }

    }

}
