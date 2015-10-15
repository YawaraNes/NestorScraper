package com.yawaranes.nestorscraper.network;

import android.util.Log;

/**
 * Logger that breaks a large text in chunks and logs each one of them instead of getting truncated
 * in logcat.
 */
public class LargeTextLogger {

    public static final int MAX_LINE_LENGTH = 4000;

    public static void d(String tag, StringBuilder log) {
        try {
            if (log.length() <= MAX_LINE_LENGTH) {
                Log.d(tag, log.toString());
            } else {
                int chunkCount = log.length() / MAX_LINE_LENGTH;
                for (int i = 0; i <= chunkCount; i++) {
                    int max = MAX_LINE_LENGTH * (i + 1);
                    if (max >= log.length()) {
                        Log.d(tag, log.substring(MAX_LINE_LENGTH * i));
                    } else {
                        Log.d(tag, log.substring(MAX_LINE_LENGTH * i, max));
                    }
                }
            }
        } catch (Exception e) {
            // A log should NEVER make the app crash, try to print the exception if logging fails.
            System.out.println(e.getMessage());
        }
    }
}
