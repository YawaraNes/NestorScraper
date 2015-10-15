package com.yawaranes.nestorscraper.fragments;

import android.util.Log;

import com.yawaranes.nestorscraper.base.BaseFragment;

public class ViewFactory {

    private static final String VIEW_PREFIX = "ViewID";

    private static ViewFactory singletonInstance = null;

    public interface ViewId {

        int HOME = 0;
        int URL_LIST = 1;
    }

    public static ViewFactory getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new ViewFactory();
        }
        return singletonInstance;
    }

    public int getHomeViewId() {
        return ViewId.HOME;
    }

    protected BaseFragment newView(int viewId) {
        BaseFragment baseFragment = null;
        switch (viewId) {
        case ViewId.HOME:
        case ViewId.URL_LIST:
            baseFragment = new UrlListFragment();
            break;
        default:
            Log.e(getClass().getSimpleName(), "Unknown " + getViewName(viewId));
        }

        if (baseFragment != null) {
            baseFragment.setViewName(getViewName(viewId));
        }

        return baseFragment;
    }

    private static String getViewName(int viewId) {
        return VIEW_PREFIX + viewId;
    }

    public BaseFragment getFragmentWithViewId(int viewId) {
        return newView(viewId);
    }
}
