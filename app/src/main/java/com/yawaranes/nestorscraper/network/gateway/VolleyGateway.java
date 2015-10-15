package com.yawaranes.nestorscraper.network.gateway;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.yawaranes.nestorscraper.network.LargeTextLogger;
import com.yawaranes.nestorscraper.network.ScraperApi;
import com.yawaranes.nestorscraper.network.request.CustomStringRequest;
import com.yawaranes.nestorscraper.network.request.ErrorResponse;

import java.io.File;
import java.util.Map;

/**
 * Gateway that implements the API using Volley as the networking library.
 */
public class VolleyGateway extends Gateway {

    public static final String TAG = VolleyGateway.class.getSimpleName();
    /**
     * Default on-disk cache directory.
     */
    private static final String DEFAULT_CACHE_DIR = "volley";

    /**
     * The default encoding fot the parameters of a GET request.
     */
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * The default socket timeout in milliseconds.
     */
    private static final int DEFAULT_TIMEOUT_MS = 5000;
    /**
     * The default number of retries.
     */
    private static final int DEFAULT_MAX_RETRIES = 1;
    /**
     * The default backoff multiplier.
     */
    private static final float DEFAULT_BACKOFF_MULT = 1f;
    /**
     * The default retry policy for GsonRequests to use.
     */
    public static final RetryPolicy RETRY_POLICY = new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS,
            DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT);

    /**
     * Global request queue for Volley, used for the requests.
     */
    private RequestQueue mRequestQueue;

    /**
     * Recommendation: pass the Application context in order to avoid possible memory leaks linked
     * to a destroying activity.
     *
     * @param context A context, preferably the Application context.
     */
    public VolleyGateway(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = newRequestQueue(context);
            VolleyLog.setTag(TAG);
            // Enabling debug always as in release mode all logs will be automatically deactivated
            VolleyLog.DEBUG = true;
        }
    }

    /**
     * Create a new request queue for Volley.
     *
     * @param context context needed to create the cache dir.
     * @return The {@link com.android.volley.RequestQueue} to be returned
     */
    private RequestQueue newRequestQueue(Context context) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        HttpStack stack = new HurlStack();
        Network network = new BasicNetwork(stack);
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        queue.start();

        return queue;
    }

    /**
     * Adds the specified request to the global queue using the default TAG.
     *
     * @param request the request to add to the queue.
     */
    protected <T> void addToRequestQueue(Request<T> request) {
        addToRequestQueue(mRequestQueue, request, TAG);
    }

    /**
     * Adds the specified request to the global queue, if tag is specified then it is used,
     * otherwise the default TAG is used.
     *
     * @param request the request to add to the queue.
     * @param tag     the tag to set to the request, empty if default tag is desired.
     */
    protected <T> void addToRequestQueue(RequestQueue queue, Request<T> request, String tag) {
        // set the default tag if tag is empty
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        logRequest("Adding request to queue:", request);

        request.setRetryPolicy(RETRY_POLICY);

        queue.add(request);
    }

    public static ErrorResponse parseVolleyError(VolleyError volleyError) {
        if (volleyError instanceof NoConnectionError) {
            return new ErrorResponse(null);
        } else if (volleyError instanceof HtmlFacadeError) {
            return new ErrorResponse(((HtmlFacadeError) volleyError).getHtml());
        }

        ErrorResponse genericError = new ErrorResponse(null);
        if (volleyError == null || volleyError.networkResponse == null
                || volleyError.networkResponse.data == null
                || volleyError.networkResponse.headers == null) {
            return genericError;
        }

        try {
            String charSet = HttpHeaderParser.parseCharset(volleyError.networkResponse.headers);
            String json = new String(volleyError.networkResponse.data, charSet);
            return fromJson(json, ErrorResponse.class);
        } catch (Exception e) {
            return genericError;
        }
    }

    @Override
    public void getSourceCode(final GatewayResponse.Listener<String> responseListener,
            final GatewayResponse.ErrorListener errorListener) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET,
                ScraperApi.sUrlGetSourceCode,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseListener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        errorListener.onErrorResponse(parseVolleyError(volleyError));
                    }
                }
        );

        addToRequestQueue(request);
    }

    /**
     * Log a request with a custom message.
     *
     * @param message the message to show with the request.
     * @param request the request to log.
     */
    protected <T> void logRequest(String message, Request<T> request) {
        try {
            StringBuilder log = new StringBuilder();

            String url = "";
            log.append("\n").append(message).append("\n");
            log.append(" \n");
            log.append("\n=============== REQUEST ================\n");
            switch (request.getMethod()) {
                case Request.Method.GET:
                    url += "GET ";
                    break;
                case Request.Method.POST:
                    url += "POST ";
                    break;
                case Request.Method.PUT:
                    url += "PUT ";
                    break;
                case Request.Method.DELETE:
                    url += "DELETE ";
                    break;
                default:
                    url += "Request.Method." + request.getMethod();
                    break;
            }
            log.append(url).append(request.getUrl()).append("\n");

            log.append("\n=============== HEADERS ================\n");
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                log.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
            }

            log.append("\n================ BODY ==================\n");
            byte[] body = request.getBody();
            if (body != null) {
                // The body encoding isn't accessible from outside the request, we assume the same
                // encoding for GET parameters will be used for POST/PUT bodies.
                String bodyStr = new String(body, DEFAULT_ENCODING);
                log.append(bodyStr);
            }

            log.append("\n============= END REQUEST ==============\n");
            log.append(" \n");

            LargeTextLogger.d(TAG, log);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
