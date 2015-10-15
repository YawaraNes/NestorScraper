package com.yawaranes.nestorscraper.network.request;

import com.yawaranes.nestorscraper.base.BaseEntity;
import com.yawaranes.nestorscraper.utils.StringUtils;

public class ErrorResponse extends BaseEntity {

    private String html = "";

    public ErrorResponse(String html) {
        this.html = html;
    }

    public boolean isHtml() {
        return !StringUtils.isEmpty(html);
    }

    public String getHtml() {
        return html;
    }
}
