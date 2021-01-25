package com.example.foodycookbook;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiParseUtil {

    private static final String MEALS = "meals";
    private static final String FOOD_TITLE = "strMeal";
    private static final String CATEGORY = "strCategory";
    private static final String TAGS = "strTags";
    private static final String RECIPE = "strInstructions";
    private static final String IMAGE_URL = "strMealThumb";
    private static final String YOUTUBE_LINK = "strYoutube";
    private static final String INGREDIENT = "strIngredient";
    private static final String SOURCE = "strSource";

    public Food getFood(String api) {
        try {
            JSONObject main = new JSONObject(api);
            String meals = main.getString(MEALS);
            JSONArray mealArray = new JSONArray(meals);
            JSONObject foodJson = mealArray.getJSONObject(0);
            String imageUrl = foodJson.getString(IMAGE_URL);
            String foodTitle = foodJson.getString(FOOD_TITLE);
            String category = foodJson.getString(CATEGORY);
            String tags = foodJson.getString(TAGS);
            String youtubeLink = foodJson.getString(YOUTUBE_LINK);
            String recipe = foodJson.getString(RECIPE);
            String source = foodJson.getString(SOURCE);
            ArrayList<String> ingredients = new ArrayList<>();

            int i = 1;
            while (i <= 20) {
                String ingredient = foodJson.getString(INGREDIENT+i);
                if (!ingredient.equals("")) {
                    ingredients.add(ingredient);
                }
                else {
                    break;
                }
                i++;
            }

            return new Food(imageUrl,foodTitle,category,tags,youtubeLink,ingredients,recipe,source);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Info","Error in parsing api");
        }
        return null;
    }
}
