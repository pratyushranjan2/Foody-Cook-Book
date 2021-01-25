package com.example.foodycookbook;

import java.util.ArrayList;

public class Food {
    private String imageUrl;
    private String foodTitle;
    private String category;
    private String tags;
    private String youtubeLink;
    private ArrayList<String> ingredients;
    private String recipe;
    private String source;

    public Food(String imageUrl, String foodTitle, String category, String tags, String youtubeLink, ArrayList<String> ingredients, String recipe, String source) {
        this.imageUrl = imageUrl;
        this.foodTitle = foodTitle;
        this.category = category;
        this.tags = tags;
        this.youtubeLink = youtubeLink;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.source = source;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFoodTitle() {
        return foodTitle;
    }

    public String getCategory() {
        return category;
    }

    public String getTags() {
        return tags;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getSource() {
        return source;
    }
}
