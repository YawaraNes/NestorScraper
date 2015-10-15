package com.yawaranes.nestorscraper.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

    private IntentUtils() {
    }

    /**
     * Launches an Intent to be opened by a browser
     * @param ctx The Context. Must be an {@link android.app.Activity}
     * @param url The URL to be loaded in the web browser
     */
    public static void launchWebIntent(Context ctx, String url) {
        if (ValidationUtils.isValidUrl(url)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            ctx.startActivity(browserIntent);
        }
    }
}
