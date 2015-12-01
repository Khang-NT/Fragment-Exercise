package com.hasbrain.test.fragment.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class Step2Fragment extends BaseFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "Step2Fragment";
    public static final String SALARY = "FIRST_NAME", SPORTS = "LAST_NAME";

    TextView salary;
    SeekBar seekBar;
    int vSalary = 0;
    CheckBox football, tennis, ping_pong, swimming, volleyball, basketball;
    EnumSet<UserInfo.Sports> sports;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step2_fragment, container, false);

        salary = (TextView) v.findViewById(R.id.tv_salary);
        seekBar =  ((SeekBar) v.findViewById(R.id.sb_salary));
        football = ((CheckBox) v.findViewById(R.id.cb1));
        tennis = ((CheckBox) v.findViewById(R.id.cb2));
        ping_pong = ((CheckBox) v.findViewById(R.id.cb3));
        swimming = ((CheckBox) v.findViewById(R.id.cb4));
        volleyball = ((CheckBox) v.findViewById(R.id.cb5));
        basketball = ((CheckBox) v.findViewById(R.id.cb6));

        v.findViewById(R.id.b_done).setOnClickListener(this);

        sports = EnumSet.noneOf(UserInfo.Sports.class);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        seekBar.setOnSeekBarChangeListener(this);

        football.setOnCheckedChangeListener(this);
        tennis.setOnCheckedChangeListener(this);
        ping_pong.setOnCheckedChangeListener(this);
        swimming.setOnCheckedChangeListener(this);
        volleyball.setOnCheckedChangeListener(this);
        basketball.setOnCheckedChangeListener(this);

    }


    @Override
    protected Bundle saveState() {
        Bundle data = new Bundle();
        data.putInt(SALARY, vSalary);
        data.putSerializable(SPORTS, sports);
        return data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_done:
                if (sports.size() > 0){
                    Bundle data = saveState();
                    FragmentCallback callback = (FragmentCallback) getActivity();
                    callback.notifyEvent(RegisterActivity.STEP2
                            , data);
                }
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        vSalary = progress * 100;
        salary.setText("Your salary: " + vSalary + " dollars");
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
                changeSports(UserInfo.Sports.FOOTBALL, isChecked);
                break;
            case R.id.cb2:
                changeSports(UserInfo.Sports.TENNIS, isChecked);
                break;
            case R.id.cb3:
                changeSports(UserInfo.Sports.PING_PONG, isChecked);
                break;
            case R.id.cb4:
                changeSports(UserInfo.Sports.SWIMMING, isChecked);
                break;
            case R.id.cb5:
                changeSports(UserInfo.Sports.VOLLEYBALL, isChecked);
                break;
            case R.id.cb6:
                changeSports(UserInfo.Sports.BASKETBALL, isChecked);
                break;
        }
    }

    private void changeSports(UserInfo.Sports s, boolean add) {
        if (add) sports.add(s);
        else sports.remove(s);
    }
}
