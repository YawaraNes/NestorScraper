package com.yawaranes.nestorscraper;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.yawaranes.nestorscraper.base.BaseActivity;
import com.yawaranes.nestorscraper.fragments.ViewFactory;
import com.yawaranes.nestorscraper.navigation.NavigationManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_text_color));
            setSupportActionBar(toolbar);
        }

        initFirstFragment();
    }

    private void initFirstFragment() {
        changeMenuEntry(ViewFactory.getInstance().getHomeViewId());
    }

    private void changeMenuEntry(int viewId) {
        NavigationManager.navigateToFragmentCleanStack(this, viewId, ViewFactory.getInstance());
    }
}
