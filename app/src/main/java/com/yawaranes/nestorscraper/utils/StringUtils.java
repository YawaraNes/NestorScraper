package com.yawaranes.nestorscraper.utils;

/** String related Utils.
 *
 * @reminder: some function use the Locale (like uppperCase). Those are documented "LOCALIZED".
 * Call init("en") to change it. (default is ES. In case of error use US)
 *
 * */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * <p>
     * Checks if a String is empty ("") or null.
     * </p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty(&quot;&quot;)        = true
     * StringUtils.isEmpty(&quot; &quot;)       = false
     * StringUtils.isEmpty(&quot;bob&quot;)     = false
     * StringUtils.isEmpty(&quot;  bob  &quot;) = false
     * </pre>
     * <p>
     * NOTE: does not trims the String. That functionality is available in isBlank().
     * </p>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return CommonUtils.isStringEmpty(str);
    }

    /**
     * <p>
     * Checks if a String contain nothing. Ie: Is null or contain only whitespace ( whitespace are the char removed by trim())
     * </p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank(&quot;&quot;)        = true
     * StringUtils.isBlank(&quot; &quot;)       = true
     * StringUtils.isBlank(&quot; \n &quot;)    = true
     * StringUtils.isBlank(&quot;bob&quot;)     = false
     * StringUtils.isBlank(&quot;  bob  &quot;) = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String contain nothing or only
     *         whitespace
     */
    public static boolean isBlank(String str) {
        return CommonUtils.isStringBlank(str);
    }

    /** Checks if a String contain nothing. Ie: Is null or contain only whitespace ( whitespace are the char removed by trim()) */
    public static boolean isBlank(CharSequence str) {
        if (str == null) {
            return true;
        }
        return isBlank(str.toString());
    }
}
