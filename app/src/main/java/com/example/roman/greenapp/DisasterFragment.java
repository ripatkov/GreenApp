package com.example.roman.greenapp;



import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.UUID;

public class DisasterFragment extends Fragment {
    private Disaster mDisaster;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private TextView mLatitudeTextView, mLongitudeTextView, mAltitudeTextView;
    public static final String EXTRA_DISASTER_ID="com.example.roman.ecologyapp.disaster_id";
    private static final int REQUEST_DATE=0;
    private static final String DIALOG_DATE = "date";

    public DisasterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID disasterId=(UUID)getArguments().getSerializable(EXTRA_DISASTER_ID);
        mDisaster=DisasterStorage.get(getActivity()).getDisaster(disasterId);
        //setHasOptionsMenu(true);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.content_main, container, false);

        mTitleField=(EditText)rootView.findViewById(R.id.disaster_title);
        mTitleField.setText(mDisaster.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDisaster.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mLatitudeTextView = (TextView)rootView.findViewById(R.id.run_latitudeTextView);
        mLongitudeTextView = (TextView)rootView.findViewById(R.id.run_longitudeTextView);
        mAltitudeTextView = (TextView)rootView.findViewById(R.id.run_altitudeTextView);

        mDateButton = (Button)rootView.findViewById(R.id.disaster_date);
        //mDateButton.setText(DateFormat.getDateTimeInstance().format(mDisaster.getDate()));
        updateDate();
        //mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mDisaster.getDate());
                dialog.setTargetFragment(DisasterFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mSolvedCheckBox=(CheckBox)rootView.findViewById(R.id.disaster_solved);
        mSolvedCheckBox.setChecked(mDisaster.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDisaster.setSolved(isChecked);
            }
        });

        return rootView;
    }
    public static DisasterFragment newInstance(UUID disasterId){
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_DISASTER_ID, disasterId);
        DisasterFragment fragment = new DisasterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        DisasterStorage.get(getActivity()).saveDisasters();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode!= Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDisaster.setDate(date);
            updateDate();
        }

    }
    private void updateDate() {
        mDateButton.setText(mDisaster.getDate().toString());
    }
}

