package com.example.xuanthanh.homeexercise_week3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.xuanthanh.homeexercise_week3.pojo.Result;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailed extends AppCompatActivity {
    private static final String HEADER_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.movie_overview)
    TextView mvOverview;
    @BindView(R.id.title)
    TextView mvTitle;
    @BindView(R.id.img_poster)
    ImageView mvPoster;
    @BindView(R.id.release_date)
    TextView mvReleaseDate;
    @BindView(R.id.rating_bar)
    RatingBar rbVote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__detailed);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            Result result = Parcels.unwrap(intent.getParcelableExtra("detail"));
            if (result != null) {
                Picasso.with(this)
                        .load(Constant.HEADER_URL_IMAGE + result.getBackdropPath())
                        .fit()
                        .centerInside()
                        .into(mvPoster);

                mvTitle.setText(result.getTitle());
                mvOverview.setText(result.getOverview());
                rbVote.setRating(result.getVoteAverage() / 2);
                mvReleaseDate.setText(getResources().getString(R.string.detail_release_date) + result.getReleaseDate());
                getSupportActionBar().setTitle(result.getTitle());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}