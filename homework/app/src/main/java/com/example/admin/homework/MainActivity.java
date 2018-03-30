package com.example.admin.homework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies();
    }
    public List<Movie> movies(){

        Gson gson = new Gson();
        List<Movie> movies = Arrays.asList(gson.fromJson(MyApp.getMessage(),Movie.class));
        return movies;
    }
}
