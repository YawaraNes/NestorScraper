package com.yawaranes.nestorscraper.network.gateway;

import com.yawaranes.nestorscraper.network.request.ErrorResponse;

/**
 * A generic response listener to use by any gateway that recovers model data from any source.
 */
public class GatewayResponse {

    public interface Listener<T> {

        /**
         * Called when a response is received.
         * @param response The response object
         */
        void onResponse(T response);
    }

    public interface ErrorListener {

        /**
         * Callback method that an error has been occurred with the user-readable message.
         * @param errorResponse The error response object
         */
        void onErrorResponse(ErrorResponse errorResponse);
    }
}
