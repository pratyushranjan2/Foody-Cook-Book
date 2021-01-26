package com.example.foodycookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


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
}
