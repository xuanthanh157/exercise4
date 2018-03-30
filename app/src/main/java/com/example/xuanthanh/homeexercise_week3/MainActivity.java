package com.example.xuanthanh.homeexercise_week3;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.xuanthanh.homeexercise_week3.R;
import com.example.xuanthanh.homeexercise_week3.FragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.movie_tabs)
    TabLayout movieTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movies");
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), this));
        viewPager.setOffscreenPageLimit(2);
        movieTabs.setupWithViewPager(viewPager);
    }

}