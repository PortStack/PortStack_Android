package com.example.cookingrecipe.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookingrecipe.Domain.DTO.RecipeDTO;
import com.example.cookingrecipe.Domain.Model.Recipe;
import com.example.cookingrecipe.Domain.Model.RecipeEntity;
import com.example.cookingrecipe.OnItemClickListener;
import com.example.cookingrecipe.R;

import java.util.List;

public class



RecipeTodayAdapter extends RecyclerView.Adapter<RecipeTodayAdapter.ViewHolder> {
    List<RecipeDTO.Request> recipeList;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecipeTodayAdapter(List<RecipeDTO.Request> recipeList) {
        this.recipeList = recipeList;
    }


    @NonNull
    @Override
    public RecipeTodayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_today, parent, false);
        return new ViewHolder(inflater);
    }



    @Override
    public void onBindViewHolder(@NonNull RecipeTodayAdapter.ViewHolder holder, int position) {
        RecipeDTO.Request recipe = recipeList.get(holder.getAdapterPosition());

        holder.recipeTitle.setText(recipe.getTitle());

        String imageURL = recipe.getThemNailUrl();
        holder.main_layout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_bg));

        if (!imageURL.isBlank()) {
            Glide.with(holder.recipeImage.getContext())
                    .load("http://211.109.43.213:8081/images" +imageURL)
                    .into(holder.recipeImage);
        }

        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(recipe.getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeTitle;
        ImageView recipeImage;
        ConstraintLayout main_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipe_today_title);
            recipeImage = itemView.findViewById(R.id.recipe_today_image);
            main_layout = itemView.findViewById(R.id.recipe_today_layout);
        }
    }
}
