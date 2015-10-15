package com.yawaranes.nestorscraper.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.yawaranes.nestorscraper.R;
import com.yawaranes.nestorscraper.helpers.ActivityHelper;
import com.yawaranes.nestorscraper.navigation.NavigationManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHelper.setRequestedOrientation(this);

        // Setting NavigationBar background color for Lollipop and above API versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.common_blue));
        }
    }

    @Override
    public void onBackPressed() {
        NavigationManager.navigateBack(this);
    }
}
