package com.yawaranes.nestorscraper.network;

import com.yawaranes.nestorscraper.network.gateway.Gateway;
import com.yawaranes.nestorscraper.network.gateway.GatewayResponse;

public class ScraperApi {

    private String URL = "http://www.visual-engin.com";

    // ########################################
    // WebServices paths
    // ########################################

    private static final String URL_WS_GET_SOURCE_CODE = "/Web";

    // ########################################
    // WebServices URLs
    // ########################################

    public static String sUrlGetSourceCode;

    private Gateway mGateway;

    public ScraperApi(Gateway gateway) {
        mGateway = gateway;
        createUrls();
    }

    public String getUrl() {
        return URL;
    }

    public void setUrl(String urlPath) {
        URL = urlPath;
        createUrls();
    }

    public void setGateway(Gateway gateway) {
        mGateway = gateway;
    }

    private void createUrls() {
        sUrlGetSourceCode = URL + URL_WS_GET_SOURCE_CODE;
    }

    public void getSourceCode(
            final GatewayResponse.Listener<String> responseListener,
            final GatewayResponse.ErrorListener errorListener) {
        mGateway.getSourceCode(responseListener, errorListener);
    }
}
