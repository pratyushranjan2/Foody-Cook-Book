package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    private static final String DATABASE = "database";
    private static final String LIKED_DATA = "likedData";
    ArrayList<String> likedFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        setTitle("Favourite Cuisines");

        SharedPreferences pref = getSharedPreferences(DATABASE, Context.MODE_PRIVATE);
        try {
            likedFoods = (ArrayList<String>)ObjectSerializer.deserialize(pref.getString(LIKED_DATA,ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Error","Unable to access database");
        }

        RecyclerView recyclerView = findViewById(R.id.favouritesRecyclerView);
        if (likedFoods != null) {
            FavouritesListAdapter adapter = new FavouritesListAdapter(this,likedFoods);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }
}