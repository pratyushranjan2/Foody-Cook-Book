package com.example.foodycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String randomFoodApi = "https://www.themealdb.com/api/json/v1/1/random.php";
    private String apiData = "";
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Start","Process started");

        DownloadRandomFoodApi task = new DownloadRandomFoodApi();
        try {
            task.execute(randomFoodApi);
        } catch (Exception e) {
            e.printStackTrace();
        }
        statusTextView = findViewById(R.id.statusTextView);
    }

    private void apiWriteback(String data) {
        apiData = data;
        if (!apiData.equals("")) {
            Log.i("Info",apiData);
        }
        else {
            Log.i("Info","Could not fetch api");
            statusTextView.setText("Error..Try reopening the app");
        }
    }

    private void apiParseWriteback(Food food) {
        if (food != null) {
//            Log.i("Image Url",food.getImageUrl());
//            Log.i("Food title",food.getFoodTitle());
//            Log.i("Category",food.getCategory());
//            Log.i("Tags",food.getTags());
              Log.i("Youtube link",food.getYoutubeLink());
//            Log.i("Recipe",food.getRecipe());
//            Log.i("Source",food.getSource());
//            for (String ingredient: food.getIngredients()) {
//                Log.i("Ingredient#",ingredient);
//            }
            redirectToFoodDetailsActivity(food);
        }
        else {
            Log.i("Parse Error","Food object received as null");
            statusTextView.setText("Error..Try reopening the app");
        }
    }

    private void redirectToFoodDetailsActivity(Food food) {
        FoodDetailsActivity.setFood(food);
        Intent intent = new Intent(this,FoodDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private class DownloadRandomFoodApi extends AsyncTask<String,Void,Void> {

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
                    data = reader.read();
                }

                apiWriteback(api);
                ApiParseUtil parseUtil = new ApiParseUtil();
                apiParseWriteback(parseUtil.getFood(api));
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}