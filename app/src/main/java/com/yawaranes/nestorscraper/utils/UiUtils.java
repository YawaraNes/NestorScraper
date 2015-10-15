package com.yawaranes.nestorscraper.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UiUtils {

    private UiUtils() {
    }

    /**
     * Hide Keyboard by code. if possible, use the function with paramter(View)
     *
     * @required defaultStaticContext MUST be an Activity
     * */
    public static void hideKeyboard(Context ctx) {
        hideKeyboard(ctx, null);
    }

    /**
     * Hide Keyboard by code.
     *
     * @required defaultStaticContext MUST be an Activity
     */
    public static void hideKeyboard(Context ctx, View view) {
        try {
            if (view == null) {
                if (ctx instanceof Activity) {
                    view = ((Activity) ctx).getCurrentFocus();
                } else {
                    Log.e(UiUtils.class.getSimpleName(),
                            "hideKeyboard aborted because Context is not an Activity");
                }
            }
            // view could null when the app is in some special state
            hideKeyboard_private(ctx, view.getWindowToken());
        } catch (Exception ignore) {
        }
    }

    private static void hideKeyboard_private(Context ctx, IBinder binder) {
        if (binder == null) {
            return;
        }
        if (ctx == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, 0);
    }
}
