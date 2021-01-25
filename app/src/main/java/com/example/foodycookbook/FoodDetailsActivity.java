package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FoodDetailsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private static final String sampleVideo = "nPGPXpbwftM";
    private static Food food;

    private static final String FOOD_TITLE = "foodTitle";
    private static final String CATEGORY = "category";
    private static final String TAGS = "tags";
    private static final String RECIPE = "recipe";
    private static final String IMAGE_URL = "imageUrl";
    private static final String YOUTUBE_LINK = "youtubeLink";
    private static final String INGREDIENTS = "ingredients";
    private static final String SOURCE = "source";

    private ImageView foodImageView;
    private TextView foodTitleTextView;
    private TextView categoryTextView;
    private TextView tagsTextView;
    private Button ingredientsButton;
    private Button recipeButton;
    private TextView sourceTextView;
    private EditText searchEditText;
    private Button searchButton;
    private ImageView heartImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        if (savedInstanceState != null) {
            food = new Food(savedInstanceState.getString(IMAGE_URL),
                    savedInstanceState.getString(FOOD_TITLE),
                    savedInstanceState.getString(CATEGORY),
                    savedInstanceState.getString(TAGS),
                    savedInstanceState.getString(YOUTUBE_LINK),
                    savedInstanceState.getStringArrayList(INGREDIENTS),
                    savedInstanceState.getString(RECIPE),
                    savedInstanceState.getString(SOURCE));
        }

        youTubeView = (YouTubePlayerView) findViewById(R.id.recipeVideoView);
        youTubeView.initialize(Config.YOUTUBE_API_KEY,this);

        foodImageView = findViewById(R.id.foodImageView);
        foodTitleTextView = findViewById(R.id.foodTitleTextView);
        categoryTextView = findViewById(R.id.categoryTextView);
        tagsTextView = findViewById(R.id.tagTextView);
        ingredientsButton = findViewById(R.id.ingredientsButton);
        recipeButton = findViewById(R.id.recipeButton);
        sourceTextView = findViewById(R.id.sourceTextView);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        heartImageView = findViewById(R.id.heartImageView);

        Glide.with(this).load(food.getImageUrl()).into(foodImageView);
        foodTitleTextView.setText(food.getFoodTitle());
        categoryTextView.setText(food.getCategory());
        tagsTextView.setText(food.getTags());
        sourceTextView.setText(food.getSource());
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(food.getYoutubeLink().substring(food.getYoutubeLink().indexOf('=')+1));
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    public static void setFood(Food f) {
        food = f;
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putString(IMAGE_URL,food.getImageUrl());
        bundle.putString(FOOD_TITLE,food.getFoodTitle());
        bundle.putString(CATEGORY,food.getCategory());
        bundle.putString(TAGS,food.getTags());
        bundle.putString(YOUTUBE_LINK,food.getYoutubeLink());
        bundle.putStringArrayList(INGREDIENTS,food.getIngredients());
        bundle.putString(RECIPE,food.getRecipe());
        bundle.putString(SOURCE,food.getSource());
    }

    public void redirectToIngredientsActivity(View view) {
        Intent intent = new Intent(this,IngredientsActivity.class);
        intent.putStringArrayListExtra(INGREDIENTS,food.getIngredients());
        startActivity(intent);
    }

    public void redirectToRecipeActivity(View view) {
        Intent intent = new Intent(this,RecipeActivity.class);
        intent.putExtra(RECIPE,food.getRecipe());
        startActivity(intent);
    }

    public void searchMeal(View view) {
        String meal = searchEditText.getText().toString();
        if (!meal.equals("")) {
            DownloadFoodApi task = new DownloadFoodApi();
            try {
                task.execute("https://www.themealdb.com/api/json/v1/1/search.php?s="+meal);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void heartClicked(View view) {
        if (view.getTag().equals("0")) {
            // user liked
            heartImageView.setImageResource(R.drawable.filled_heart);
            view.setTag("1");
        }
        else {
            // remove from liked list
            heartImageView.setImageResource(R.drawable.hollow_heart);
            view.setTag("0");
        }
    }

    private class DownloadFoodApi extends AsyncTask<String,Void,Void> {

        String api = "";

        @Override
        protected Void doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    api += current;
                    if (current == '}') {
                        api += "]}";
                        break;
                    }
                    data = reader.read();
                }
                ApiParseUtil parseUtil = new ApiParseUtil();
                Food food = parseUtil.getFood(api);
                if (food != null) {
                    updateUI(food);
                }
                else {
                    showToast(getString(R.string.no_search_result_found));
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void updateUI(Food food) {
        setFood(food);
        Intent intent = new Intent(this,FoodDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}