package com.yawaranes.nestorscraper.model;

import com.yawaranes.nestorscraper.base.BaseEntity;

public class UrlEntity extends BaseEntity {

    private String urlAddress;

    public UrlEntity(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getUrlAddress() {
        return urlAddress;
    }
}
