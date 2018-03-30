package com.example.xuanthanh.homeexercise_week3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.xuanthanh.homeexercise_week3.pojo.Result;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class MovieDetailed extends AppCompatActivity {

    private static final String HEADER_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__detailed);
        Result result = (Result) Parcels.unwrap(getIntent().getParcelableExtra("Result"));

        ImageView imagePoster = findViewById(R.id.img_poster);
        TextView title = findViewById(R.id.title);
        TextView releaseDate = findViewById(R.id.release_date);
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        TextView movieOverview = findViewById(R.id.movie_overview);

        Picasso.with(this).load(HEADER_URL_IMAGE + result.getBackdropPath()).into(imagePoster);
        title.setText(result.getTitle());
        releaseDate.setText(result.getReleaseDate());
        ratingBar.setRating(result.getVoteAverage() / 2);
        movieOverview.setText(result.getOverview());


    }
}
