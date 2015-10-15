package com.yawaranes.nestorscraper.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yawaranes.nestorscraper.utils.UiUtils;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    private String viewName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return mRootView;
    }

    public abstract void loadUIComponents();

    public abstract void loadUITexts();

    protected void addViewListener(View view) {
        // Set up touch listener for non-text box views to hide keyboard
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    UiUtils.hideKeyboard(getActivity(), v);
                    return false;
                }
            });
        }

        // If a layout container, iterate over children and seed recursion
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                addViewListener(innerView);
            }
        }
    }

    @Override
    public void onPause() {
        UiUtils.hideKeyboard(getActivity());
        super.onPause();
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}

