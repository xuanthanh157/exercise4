package com.example.xuanthanh.homeexercise_week3;

import android.content.Intent;
import android.os.Parcel;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.xuanthanh.homeexercise_week3.pojo.Movie;
import com.example.xuanthanh.homeexercise_week3.pojo.Result;
import com.example.xuanthanh.homeexercise_week3.pojo.SampleFragmentPagerAdapter;
import com.google.gson.Gson;

import org.parceler.Parcels;

public class MovieList extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),MovieList.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }


}
