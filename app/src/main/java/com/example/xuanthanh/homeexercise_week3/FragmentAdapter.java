package com.example.xuanthanh.homeexercise_week3;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class FragmentAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private static final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{Constant.TAG_NOW_PLAYING, Constant.TAG_TOP_RATE};

    private Context context;

    public FragmentAdapter(FragmentManager Fragment, Context context) {
        super(Fragment);
        this.context = context;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return MovieFragment.newInstance(tabTitles[position]);
    }

}