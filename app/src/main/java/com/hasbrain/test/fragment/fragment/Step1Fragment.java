package com.hasbrain.test.fragment.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hasbrain.test.fragment.R;
import com.hasbrain.test.fragment.activity.RegisterActivity;
import com.hasbrain.test.fragment.model.FragmentCallback;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Khang on 22/11/2015.
 */
public class Step1Fragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Step1Fragment";
    public static final String FIRST_NAME = "FIRST_NAME", LAST_NAME = "LAST_NAME",
            EMAIL = "EMAIL", PHONE = "PHONE", SEX = "SEX";
    public static final int MALE = 0, FEMALE = 1;

    private EditText firstName, lastName, email, phone;
    private RadioGroup sex;
    private Button next;

    boolean isViewCreated = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step1_fragment, container, false);
        firstName = (EditText) v.findViewById(R.id.ed_fname);
        lastName = (EditText) v.findViewById(R.id.ed_lname);
        email = (EditText) v.findViewById(R.id.ed_email);
        phone = (EditText) v.findViewById(R.id.ed_phone);
        sex = (RadioGroup) v.findViewById(R.id.rg_sex);
        next = (Button) v.findViewById(R.id.b_next);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next.setOnClickListener(this);
        isViewCreated = true;
        if (savedInstanceState != null){
            Toast.makeText(getActivity(), TAG + " onViewStateRestored", Toast.LENGTH_SHORT).show();
            firstName.setText(savedInstanceState.getString(FIRST_NAME));
            lastName.setText(savedInstanceState.getString(LAST_NAME));
            email.setText(savedInstanceState.getString(EMAIL));
            phone.setText(savedInstanceState.getString(PHONE));
            if (savedInstanceState.getInt(SEX) == FEMALE)
                sex.check(R.id.rb_female);
            else
                sex.check(R.id.rb_male);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            Toast.makeText(getActivity(), TAG + " onViewStateRestored", Toast.LENGTH_SHORT).show();
            firstName.setText(savedInstanceState.getString(FIRST_NAME));
            lastName.setText(savedInstanceState.getString(LAST_NAME));
            email.setText(savedInstanceState.getString(EMAIL));
            phone.setText(savedInstanceState.getString(PHONE));
            if (savedInstanceState.getInt(SEX) == FEMALE)
                sex.check(R.id.rb_female);
            else
                sex.check(R.id.rb_male);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Bundle data = getData();
        if (data != null)
            outState.putAll(getData());
        //Toast.makeText(getActivity(), TAG + " onSaveInstanceState", Toast.LENGTH_SHORT).show();

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null){
            //Toast.makeText(getActivity(), TAG + " onViewStateRestored", Toast.LENGTH_SHORT).show();
            firstName.setText(savedInstanceState.getString(FIRST_NAME));
            lastName.setText(savedInstanceState.getString(LAST_NAME));
            email.setText(savedInstanceState.getString(EMAIL));
            phone.setText(savedInstanceState.getString(PHONE));
            if (savedInstanceState.getInt(SEX) == FEMALE)
                sex.check(R.id.rb_female);
            else
                sex.check(R.id.rb_male);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_next:
                if (checkInput()){
                    hideKeybroad();
                    Bundle data = getData();
                    FragmentCallback callback = (FragmentCallback) getActivity();
                    callback.notifyEvent(RegisterActivity.STEP1
                            , data);
                }
                break;
        }
    }

    /**
     * Check input and warning error.
     * @return return true if input is ok, else return false;
     */
    private boolean checkInput(){
        boolean isOk = true;

        if (isEmpty(firstName.getText())){
            firstName.setError("Please enter your name");
            isOk = false;
        } else firstName.setError(null);

        if (isEmpty(lastName.getText())){
            lastName.setError("Please enter your name");
            isOk = false;
        } else lastName.setError(null);

        if (!isValidEmail(email.getText())){
            email.setError("Your email is invalid");
            isOk = false;
        } else email.setError(null);

        if (!isValidPhone(phone.getText())){
            phone.setError("Your phone number is invalid");
            isOk = false;
        } else phone.setError(null);

        return isOk;
    }

    private boolean isValidEmail(CharSequence target) {
        return !isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPhone(CharSequence target) {
        final String regex = "^[+|0][0-9]{10,13}$";
        return !isEmpty(target) && target.toString().matches(regex);
    }

    /**
     *  Get all input data
     * @return return data as Bundle
     */
    private Bundle getData(){
        if (!isViewCreated) return null;
        Bundle data = new Bundle();
        data.putString(FIRST_NAME, firstName.getText().toString());
        data.putString(LAST_NAME, lastName.getText().toString());
        data.putString(EMAIL, email.getText().toString());
        data.putString(PHONE, phone.getText().toString());
        data.putInt(SEX, sex.getCheckedRadioButtonId() == R.id.rb_female ?
                FEMALE : MALE);
        return data;
    }

    private void hideKeybroad(){
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset(){
        try {
            firstName.setText(null);
            lastName.setText(null);
            email.setText(null);
            phone.setText(null);
        } catch (Exception e) {
            System.out.println("NULL");
        }
    }
}
