package com.taboola.sdksample.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.taboola.sdksample.AppConfig;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    MainPagerAdapter(FragmentManager fm, List<AppConfig.TabConfig> tabsConfig) {
        super(fm);

        for (AppConfig.TabConfig tabConfig : tabsConfig) {
            fragmentList.add(TabFragment.newInstance(tabConfig.getPlacement()));
            fragmentTitleList.add(tabConfig.getTitle());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
