package com.hasbrain.test.fragment.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hasbrain.test.fragment.R;
import com.hasbrain.test.fragment.activity.RegisterActivity;
import com.hasbrain.test.fragment.model.FragmentCallback;

/**
 * Created by Khang on 22/11/2015.
 */
public class Step3Fragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "Step3Fragment";
    public static final String BUTTON_EVENT = "BUTTON_EVENT", SEND_MAIL = "SEND_MAIL", RESET = "RESET";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step3_fragment, container, false);
        v.findViewById(R.id.b_restart).setOnClickListener(this);
        v.findViewById(R.id.b_sendmail).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        final FragmentCallback callback = (FragmentCallback) getActivity();
        final Bundle event = new Bundle();
        switch (v.getId()){
            case R.id.b_restart:
                event.putString(BUTTON_EVENT, RESET);
                break;
            case R.id.b_sendmail:
                event.putString(BUTTON_EVENT, SEND_MAIL);
                break;
        }
        callback.notifyEvent(RegisterActivity.STEP3, event);
    }
}
