package com.yawaranes.nestorscraper.network.request;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.yawaranes.nestorscraper.network.LargeTextLogger;
import com.yawaranes.nestorscraper.network.gateway.VolleyGateway;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Custom Volley String request
 */
public class CustomStringRequest extends StringRequest {

    protected final Response.Listener<String> listener;
    protected String url = "";

    /**
     * Create a new Gson Json-parser request.
     *
     * @param method the Http method see {@link com.android.volley.Request.Method}
     * @param url request url.
     * @param listener response listener.
     * @param errorListener error listener.
     */
    public CustomStringRequest(final int method, final String url,
                               final Response.Listener<String> listener,
                               final Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.listener = listener;
        this.url = url;
    }

    @Override
    protected Response<String> parseNetworkResponse(final NetworkResponse response) {
        try {
            String responseData;
            try {
                responseData = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException var4) {
                responseData = new String(response.data);
            }

            logResponse("Request finished with response from the server:", response, responseData);

            return Response.success(responseData, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            Log.e(VolleyGateway.TAG, e.getMessage(), e);
            return Response.error(new ParseError(e));
        }
    }

    /**
     * Log the response of this request with a custom message.
     *
     * @param message the message to show with the request.
     * @param response the response to log.
     * @param json the json received in the body response.
     */
    protected void logResponse(final String message, final NetworkResponse response,
            final String json) {
        StringBuilder log = new StringBuilder();

        String url = "";
        log.append("\n").append(message).append("\n");
        log.append(" \n");
        log.append("\n=============== RESPONSE ===============\n");
        log.append(url).append(this.url).append("\n");
        log.append("HTTP Status Code: ");
        if (response != null) {
            log.append(response.statusCode).append("\n");
        }

        log.append("\n=============== HEADERS ================\n");
        if (response != null && response.headers != null) {
            for (Map.Entry<String, String> header : response.headers.entrySet()) {
                log.append(header.getKey()).append("=").append(header.getValue()).append("\n");
            }
        }

        log.append("\n================= BODY =================\n");
        if (json != null) {
            log.append(json).append("\n");
        }

        log.append("\n============= END RESPONSE =============\n");
        log.append(" \n");

        LargeTextLogger.d(VolleyGateway.TAG, log);
    }
}
