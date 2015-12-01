package com.hasbrain.test.fragment.fragment;

/**
 * Created by Khang on 01/12/2015.
 */
public enum FragmentState {
    STEP1("STEP 1"), STEP2("STEP 2"), STEP3("STEP 3");

    private String name;

    FragmentState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
