package com.example.foodycookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RecipeActivity extends AppCompatActivity {

    private static final String RECIPE = "recipe";
    private String recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        setTitle("Recipe");

        if (savedInstanceState != null) {
            recipe = savedInstanceState.getString(RECIPE);
        }

        Intent intent = getIntent();
        recipe = intent.getStringExtra(RECIPE);

        if (recipe != null) {
            TextView textView = findViewById(R.id.completeReipeTextView);
            textView.setText(recipe);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RECIPE,recipe);
    }
}