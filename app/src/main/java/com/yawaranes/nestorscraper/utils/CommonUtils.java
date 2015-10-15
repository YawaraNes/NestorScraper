package com.yawaranes.nestorscraper.utils;

/** Private Utils for the Lib Utils (not a joke) */
public class CommonUtils {

    private CommonUtils() {
    }

    /**@return true if null or "" */
    static boolean isStringEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**@return true if null or contain only white space. (as defined by String.trim() )   */
    static boolean isStringBlank(String str) {
        if (str == null) {
            return true;
        }
        return str.trim().length() == 0;
    }
}
