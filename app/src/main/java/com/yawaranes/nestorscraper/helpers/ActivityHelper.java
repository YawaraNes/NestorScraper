package com.yawaranes.nestorscraper.helpers;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.yawaranes.nestorscraper.R;

public class ActivityHelper {

    public static void setRequestedOrientation(Activity activity) {
        // Different screen orientation if the device is tablet or not
        if (!activity.getResources().getBoolean(R.bool.is_tablet)) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }
}
