package com.hasbrain.test.fragment.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hasbrain.test.fragment.R;
import com.hasbrain.test.fragment.fragment.FragmentState;
import com.hasbrain.test.fragment.fragment.Step1Fragment;
import com.hasbrain.test.fragment.fragment.Step2Fragment;
import com.hasbrain.test.fragment.fragment.Step3Fragment;
import com.hasbrain.test.fragment.model.FragmentCallback;
import com.hasbrain.test.fragment.model.UserInfo;

import java.util.EnumSet;

public class RegisterActivity extends AppCompatActivity implements FragmentCallback{
    //Fragment ID
    public static final int STEP1 = 0, STEP2 = 1, STEP3 = 2;
    private static final String STATE = "FRAGMENT_STATE";

    Fragment step1Fragment, step2Fragment, step3Fragment;
    static UserInfo info;
    FragmentState fragmentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        if (info == null)
            info = new UserInfo();


        // Restore fragment by Tag
        if (savedInstanceState != null){
            step1Fragment = getSupportFragmentManager().getFragment(savedInstanceState, "STEP1");
            step2Fragment = getSupportFragmentManager().getFragment(savedInstanceState, "STEP2");
            step3Fragment = getSupportFragmentManager().getFragment(savedInstanceState, "STEP3");

            fragmentState = (FragmentState) savedInstanceState.getSerializable(STATE);
            if (fragmentState != null)
                setTitle(fragmentState.getName());

        } else {
            step1Fragment = new Step1Fragment();
            switchFragment(step1Fragment, false);

            fragmentState = FragmentState.STEP1;
            setTitle(fragmentState.getName());
        }
    }

    private void switchFragment(Fragment fragmentToShow, boolean saveThisState) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragmentToShow);
        if (saveThisState)
            ft.addToBackStack(fragmentState.getName());
        ft.commit();
    }

    @Override
    public void notifyEvent(int fragmentId, Bundle extra) {
        switch(fragmentId){
            case STEP1:
                if (extra != null){
                    info.setFirstName(extra.getString(Step1Fragment.FIRST_NAME))
                            .setLastName(extra.getString(Step1Fragment.LAST_NAME))
                            .setEmail(extra.getString(Step1Fragment.EMAIL))
                            .setPhoneNumber(extra.getString(Step1Fragment.PHONE))
                            .setSex(extra.getInt(Step1Fragment.SEX) == Step1Fragment.MALE);
                }
                // show Step2 fragment
                if (step2Fragment == null)
                    step2Fragment = new Step2Fragment();
                switchFragment(step2Fragment, true);

                fragmentState = FragmentState.STEP2;
                setTitle(fragmentState.getName());
                break;

            case STEP2:
                if (extra != null){
                    info.setSalary(extra.getInt(Step2Fragment.SALARY))
                            .setSports((EnumSet<UserInfo.Sports>) extra.getSerializable(Step2Fragment.SPORTS));
                }
                // show Step3 fragment
                if (step3Fragment == null)
                    step3Fragment = new Step3Fragment();
                switchFragment(step3Fragment, true);

                fragmentState = FragmentState.STEP3;
                setTitle(fragmentState.getName());
                break;

            case STEP3:
                if (extra != null){
                    String buttonEvent = extra.getString(Step3Fragment.BUTTON_EVENT);
                    if (Step3Fragment.RESET.equals(buttonEvent)) {

                        Step1Fragment.requestResetInput = true;

                        FragmentManager fm = getSupportFragmentManager();
                        while (fm.popBackStackImmediate()) {
                            //Pop all
                        }

                        fragmentState = FragmentState.STEP1;
                        setTitle(fragmentState.getName());
                    } else
                    // Button Send mail click
                        if (Step3Fragment.SEND_MAIL.equals(buttonEvent)) {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", info.email, null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "User's registration info");

                        String content = info.firstName + "_" + info.lastName + "\n";
                        content += info.phoneNumber + "\n";
                        content += info.salary + " dollars";

                        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                    }
                }
                break;
        }
    }

    /**
     *  save instance state of fragment
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (step1Fragment != null && step1Fragment.isAdded())
            getSupportFragmentManager().putFragment(outState, "STEP1", step1Fragment);
        if (step2Fragment != null && step2Fragment.isAdded())
            getSupportFragmentManager().putFragment(outState, "STEP2", step2Fragment);
        if (step3Fragment != null && step3Fragment.isAdded())
            getSupportFragmentManager().putFragment(outState, "STEP3", step3Fragment);

        outState.putSerializable(STATE, fragmentState);
    }


}
