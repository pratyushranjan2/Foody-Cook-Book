package com.example.foodycookbook;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListAdapter.ViewHolder> {

    Context context;
    ArrayList<String> favourites;

    public FavouritesListAdapter(Context context, ArrayList<String> favourites) {
        this.context = context;
        this.favourites = favourites;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.favourites_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.favouriteTextView.setText(favourites.get(position));
        holder.favouriteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadApi(holder.favouriteTextView.getText().toString()).execute("https://www.themealdb.com/api/json/v1/1/search.php?s=");
            }
        });
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView favouriteTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favouriteTextView = itemView.findViewById(R.id.favouriteTextView);
        }
    }

    private class DownloadApi extends AsyncTask<String,Void,Void> {

        String name;
        DownloadApi(String name) {
            this.name = name;
        }

        @Override
        protected Void doInBackground(String... urls) {
            String api = "";
            try {
                URL url = new URL(urls[0]+name);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    api += current;
                    data = reader.read();
                }

                ApiParseUtil parseUtil = new ApiParseUtil();
                Food food = parseUtil.getFood(api);
                if (food != null) {
                    FoodDetailsActivity.setFood(food);
                    Intent intent = new Intent(context,FoodDetailsActivity.class);
                    context.startActivity(intent);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
