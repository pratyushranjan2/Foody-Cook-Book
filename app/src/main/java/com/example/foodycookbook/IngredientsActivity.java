package com.example.foodycookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {

    private static final String INGREDIENTS = "ingredients";
    ArrayList<String> ingredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        setTitle("Ingredients");

        if (savedInstanceState != null) {
            ingredients = savedInstanceState.getStringArrayList(INGREDIENTS);
        }

        Intent intent = getIntent();
        ingredients = intent.getStringArrayListExtra(INGREDIENTS);

        if (ingredients != null) {
            RecyclerView recyclerView = findViewById(R.id.ingredientsRecyclerView);
            IngredientListAdapter adapter = new IngredientListAdapter(this,ingredients);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(INGREDIENTS,ingredients);
    }
}