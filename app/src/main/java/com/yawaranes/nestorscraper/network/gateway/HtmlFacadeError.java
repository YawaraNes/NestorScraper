package com.yawaranes.nestorscraper.network.gateway;

import com.android.volley.VolleyError;

public class HtmlFacadeError extends VolleyError {

    private String mHtml;

    public HtmlFacadeError(String html) {
        mHtml = html;
    }

    public String getHtml() {
        return mHtml;
    }
}
