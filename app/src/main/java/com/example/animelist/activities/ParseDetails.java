package com.example.animelist.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.animelist.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ParseDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_details);

        //Hide default Action Bar
        getSupportActionBar().hide();

        //Receive data
        String name = getIntent().getExtras().getString("anime_name");
        String description = getIntent().getExtras().getString("anime_description");
        String studio = getIntent().getExtras().getString("anime_studio");
        String category = getIntent().getExtras().getString("anime_category");
        String image_url = getIntent().getExtras().getString("anime_img");
        String nb_episode = getIntent().getExtras().getString("anime_nb_episode");
        String rating = getIntent().getExtras().getString("anime_rating");

        //Init values
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolBar);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_name = findViewById(R.id.pd_anime_name);
        TextView tv_description = findViewById(R.id.pd_description);
        TextView tv_category = findViewById(R.id.pd_categorie);
        TextView tv_rating = findViewById(R.id.pd_rating);
        ImageView tv_img = findViewById(R.id.pd_thumbnail);
        TextView tv_studio = findViewById(R.id.pd_studio);
        TextView tv_nb_episode = findViewById(R.id.nb_episode);

        //Setting values to each
        tv_name.setText(name);
        tv_category.setText(category);
        tv_description.setText(description);
        tv_studio.setText(studio);
        tv_rating.setText(rating);
        tv_nb_episode.setText(nb_episode);

        collapsingToolbarLayout.setTitle(name);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        //Set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(tv_img);
    }
}