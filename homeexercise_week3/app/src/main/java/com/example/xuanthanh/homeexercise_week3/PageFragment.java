package com.example.xuanthanh.homeexercise_week3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xuanthanh.homeexercise_week3.pojo.Movie;
import com.example.xuanthanh.homeexercise_week3.pojo.Result;
import com.google.gson.Gson;
import com.squareup.picasso.OkHttpDownloader;

import org.parceler.Parcels;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ADMIN on 3/30/2018.
 */

public class PageFragment extends Fragment implements MovieInformationAdapter.MovieItemClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final String TAG = "d";

    private String title;
    String url;
    OkHttpClient Client = new OkHttpClient();
    Request request;
    MovieInformationAdapter adapter;


    public static PageFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, title);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_page, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            final Movie movie = gson.fromJson(response.body().charStream(), Movie.class);
                            if (movie != null) {
                                Log.d(TAG, "onResponse: ");
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.clear();;
                                        adapter.addAll(movie.getResults());
                                        swipeContainer.setRefreshing(false);
                                    }
                                });

                            }
                        }

                    }
                });

            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        if (title.equals(Constants.NOW_PLAYING)) {
            url = Constants.URL_NOW_PLAYING;
        } else {
            url = Constants.URL_TOP_RATE;
        }
        Log.d(TAG, "onCreateView: " + url);
        Client = new OkHttpClient();
        request = new Request.Builder().url(url).build();

        Client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    final Movie movie = gson.fromJson(response.body().charStream(), Movie.class);
                    if (movie != null) {
                        Log.d(TAG, "onResponse: ");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new MovieInformationAdapter(movie.getResults(), getActivity(), PageFragment.this);
                                recyclerView.setAdapter(adapter);
                            }
                        });

                    }
                }

            }
        });
        return view;
    }

    @Override
    public void onMovieClick(Result result) {
        Intent intent = new Intent(getActivity(), MovieDetailed.class);
        intent.putExtra("Result", Parcels.wrap(result));
        startActivity(intent);
    }
}