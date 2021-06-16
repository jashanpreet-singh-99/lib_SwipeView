package com.ck.dev.swipeviewdemo.utils;

import android.util.Log;

public class Config {

    public static final String TAG_SWIPE_DETECTOR = "message_swipe_detector";

    public static void LOG(String tag, String message, boolean error) {
        if (error)
            Log.e(tag, message);
        else
            Log.d(tag, message);
    }

}
