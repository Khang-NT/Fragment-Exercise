package com.hasbrain.test.fragment.model;

import android.os.Bundle;

/**
 * Created by Khang on 22/11/2015.
 */
public interface FragmentCallback {
    /**
     * Callback from fragment
     * @param fragmentId the id of fragment
     * @param extra extra info
     */
    void notifyEvent(int fragmentId, Bundle extra);
}
