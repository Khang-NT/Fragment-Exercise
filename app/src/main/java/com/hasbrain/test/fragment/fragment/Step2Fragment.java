package com.hasbrain.test.fragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hasbrain.test.fragment.R;
import com.hasbrain.test.fragment.activity.RegisterActivity;
import com.hasbrain.test.fragment.model.FragmentCallback;
import com.hasbrain.test.fragment.model.UserInfo;

import java.util.EnumSet;

/**
 * Created by Khang on 22/11/2015.
 */
public class Step2Fragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "Step2Fragment";
    public static final String SALARY = "FIRST_NAME", SPORTS = "LAST_NAME";

    TextView salary;
    SeekBar seekBar;
    int vsalary = 0;
    CheckBox football, tennis, ping_pong, swimming, volleyball, basketball;
    EnumSet<UserInfo.Sports> sports;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step2_fragment, container, false);

        salary = (TextView) v.findViewById(R.id.tv_salary);

        seekBar =  ((SeekBar) v.findViewById(R.id.sb_salary));
        seekBar.setOnSeekBarChangeListener(this);

        football = ((CheckBox) v.findViewById(R.id.cb1));
        football.setOnCheckedChangeListener(this);

        tennis = ((CheckBox) v.findViewById(R.id.cb2));
        tennis.setOnCheckedChangeListener(this);

        ping_pong = ((CheckBox) v.findViewById(R.id.cb3));
        ping_pong.setOnCheckedChangeListener(this);

        swimming = ((CheckBox) v.findViewById(R.id.cb4));
        swimming.setOnCheckedChangeListener(this);

        volleyball = ((CheckBox) v.findViewById(R.id.cb5));
        volleyball.setOnCheckedChangeListener(this);

        basketball = ((CheckBox) v.findViewById(R.id.cb6));
        basketball.setOnCheckedChangeListener(this);

        v.findViewById(R.id.b_done).setOnClickListener(this);

        sports = EnumSet.noneOf(UserInfo.Sports.class);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(getData());
        //Toast.makeText(getActivity(), TAG + " onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        //Toast.makeText(getActivity(), TAG + " onViewStateRestored", Toast.LENGTH_SHORT).show();
        if (savedInstanceState != null){
            vsalary = savedInstanceState.getInt(SALARY);
            sports = (EnumSet<UserInfo.Sports>) savedInstanceState.getSerializable(SPORTS);

            seekBar.setProgress(vsalary / 100);
            salary.setText("Your salary: " + vsalary + " dollars");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_done:
                if (sports.size() > 0){
                    Bundle data = getData();
                    FragmentCallback callback = (FragmentCallback) getActivity();
                    callback.notifyEvent(RegisterActivity.STEP2
                            , data);
                }
                break;
        }
    }

    /**
     *  Get all input data
     * @return return data as Bundle
     */
    private Bundle getData(){
        Bundle data = new Bundle();
        data.putInt(SALARY, vsalary);
        data.putSerializable(SPORTS, sports);
        return data;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        vsalary = progress * 100;
        if (fromUser)
            salary.setText("Your salary: " + vsalary + " dollars");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cb1:
                if (isChecked) sports.add(UserInfo.Sports.FOOTBALL);
                else sports.remove(UserInfo.Sports.FOOTBALL);
                break;
            case R.id.cb2:
                if (isChecked) sports.add(UserInfo.Sports.TENNIS);
                else sports.remove(UserInfo.Sports.TENNIS);
                break;
            case R.id.cb3:
                if (isChecked) sports.add(UserInfo.Sports.PING_PONG);
                else sports.remove(UserInfo.Sports.PING_PONG);
                break;
            case R.id.cb4:
                if (isChecked) sports.add(UserInfo.Sports.SWIMMING);
                else sports.remove(UserInfo.Sports.SWIMMING);
                break;
            case R.id.cb5:
                if (isChecked) sports.add(UserInfo.Sports.VOLLEYBALL);
                else sports.remove(UserInfo.Sports.VOLLEYBALL);
                break;
            case R.id.cb6:
                if (isChecked) sports.add(UserInfo.Sports.BASKETBALL);
                else sports.remove(UserInfo.Sports.BASKETBALL);
                break;
        }
    }
}
