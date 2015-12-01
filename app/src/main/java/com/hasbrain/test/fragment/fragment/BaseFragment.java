package com.hasbrain.test.fragment.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Khang on 30/11/2015.
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isViewDestroyed = true;


    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewDestroyed = false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //if (savedInstanceState != null)
        //    onRestoreState(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        isViewDestroyed = true;
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (!isViewDestroyed)
            savedInstanceState.putAll(saveState());
    }

    //protected abstract void onRestoreState(Bundle savedInstanceState);
    protected abstract Bundle saveState();
}
