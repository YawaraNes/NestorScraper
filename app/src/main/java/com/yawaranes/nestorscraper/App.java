package com.yawaranes.nestorscraper;

import android.app.Application;

import com.yawaranes.nestorscraper.network.ScraperApi;
import com.yawaranes.nestorscraper.network.gateway.VolleyGateway;

public class App extends Application {

    private static Application appContext;
    private static ScraperApi scraperApi;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        // Setting API call system
        VolleyGateway volleyGateway = new VolleyGateway(this);
        scraperApi = new ScraperApi(volleyGateway);
    }

    public static Application getContext() {
        return appContext;
    }

    public static ScraperApi getApi() {
        return scraperApi;
    }
}
