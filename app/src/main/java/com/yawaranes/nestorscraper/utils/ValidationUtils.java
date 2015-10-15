package com.yawaranes.nestorscraper.utils;

import android.util.Patterns;

public class ValidationUtils {

    private ValidationUtils() {
    }

    /**
     * Checks if a provided URL is valid or not using Regexp
     * @param url The provided potential URL to be checked
     * @return If it is valid or not
     */
    public static boolean isValidUrl(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }
}
