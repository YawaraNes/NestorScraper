package com.yawaranes.nestorscraper.network.gateway;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * A gateway to a source of model objects, it can be an implementation of a remote api using a
 * networking library, an access to a local database, a fake implementation with mocked data, ...
 *
 * A decision was made to not create an interface so in tests we can override only the calls we
 * need instead of implementing the whole interface.
 */
public abstract class Gateway {

    private static final String TAG = Gateway.class.getSimpleName();

    /** Default Gson parser */
    protected static final Gson sGson = new GsonBuilder().create();

    public void getSourceCode(
            final GatewayResponse.Listener<String> responseListener,
            final GatewayResponse.ErrorListener errorListener) {
    }

    public static <T> T fromJson(String response, Class<T> klass) {
        T result = null;
        try {
            result = sGson.fromJson(response, klass);
        } catch (JsonSyntaxException | NullPointerException e) {
            manageParseJsonException(e, false);
        }
        return result;
    }

    public static void manageParseJsonException(Exception e, boolean isList) {
        Log.e(TAG, "Error fromJson" + (isList ? "List" : "") + ": " + e);
    }
}
