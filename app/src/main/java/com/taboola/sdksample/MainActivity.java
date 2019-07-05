package com.taboola.sdksample;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        AppConfig appConfig = ((SampleApplication) getApplication()).getAppConfig();

        initViewPager(appConfig.getTabsConfig());
    }

    private void initViewPager(List<AppConfig.TabConfig> tabsConfig) {
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), tabsConfig);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);

        if (tabsConfig.size() < 2) {
            tabLayout.setVisibility(View.GONE);
        } else {
            tabLayout.setVisibility(View.VISIBLE);
        }
    }

}
