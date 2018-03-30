package com.example.xuanthanh.homeexercise_week3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.example.xuanthanh.homeexercise_week3.Constant;
import com.example.xuanthanh.homeexercise_week3.R;
import com.example.xuanthanh.homeexercise_week3.MovieDetailed;
import com.example.xuanthanh.homeexercise_week3.MovieInformationAdapter;
import com.example.xuanthanh.homeexercise_week3.pojo.Movie;
import com.example.xuanthanh.homeexercise_week3.pojo.Result;

import org.parceler.Parcels;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MovieFragment extends Fragment implements MovieInformationAdapter.MovieItemClickListener {

    public static final String ARG_PAGE = "ARG_PAGE_TITLE";
    private static final String TAG = "fragment";

    private String title, urlData;
    private OkHttpClient okHttpClient;
    private Request request;
    private Movie movie;
    private MovieInformationAdapter adapter;
    private Gson gson;

    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    public static MovieFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PAGE, title);
        MovieFragment movieFragment = new MovieFragment();
        movieFragment.setArguments(bundle);
        return movieFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = this.getArguments().getString(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragment, container, false);
        ButterKnife.bind(this, view);
        initView();
        loadData();
        return view;
    }

    private void initView() {
        rvMovie.setHasFixedSize(true);
        rvMovie.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rvMovie.setItemAnimator(new DefaultItemAnimator());
        swipeContainer.setOnRefreshListener(() -> refresh());
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void refresh() {
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                movie = gson.fromJson(response.body().charStream(), Movie.class);
                if (movie != null) {
                    getActivity().runOnUiThread(() -> {
                        adapter.clear();
                        adapter.addAll(movie.getResults());
                        swipeContainer.setRefreshing(false);
                    });
                }
            }
        });
    }

    private void loadData() {
        urlData = title.equals(Constant.TAG_NOW_PLAYING) ?
                Constant.URL_NOW_PLAYING_API : Constant.URL_TOP_RATE_API;
        okHttpClient = new OkHttpClient();
        request = new Request.Builder()
                .url(urlData)
                .build();

        gson = new Gson();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    movie = gson.fromJson(response.body().charStream(), Movie.class);
                    if (movie != null) {
                        getActivity().runOnUiThread(() -> {
                            adapter = new MovieInformationAdapter(MovieFragment.this.getContext(),
                                    movie.getResults(), MovieFragment.this);
                            rvMovie.setAdapter(adapter);
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(Result result) {
        Intent detailIntent = new Intent(this.getContext(), MovieDetailed.class);
        detailIntent.putExtra("detail", Parcels.wrap(result));
        startActivity(detailIntent);
    }
}
