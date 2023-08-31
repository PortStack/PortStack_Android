package com.example.cookingrecipe.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookingrecipe.BuildConfig;
import com.example.cookingrecipe.Domain.DTO.RecipeDTO;
import com.example.cookingrecipe.Domain.Model.Recipe;
import com.example.cookingrecipe.OnFavoriteIconClickListener;
import com.example.cookingrecipe.OnItemClickListener;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Room.AppDatabase;
import com.example.cookingrecipe.Room.DAO.RecipeDao;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    List<RecipeDTO.Request> recipeList;
    AppDatabase db;

    Boolean isFavorite;
    private OnItemClickListener itemClickListener;
    private OnFavoriteIconClickListener favoriteIconClickListener;


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnFavoriteIconClickListener(OnFavoriteIconClickListener listener) {
        this.favoriteIconClickListener = listener;
    }

    public RecipeListAdapter(List<RecipeDTO.Request> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_favorite, parent, false);
        return new ViewHolder(inflater);
    }


    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.ViewHolder holder, int position) {
        RecipeDTO.Request recipe = recipeList.get(holder.getAdapterPosition());
        isFavorite = recipe.getLikeState();
        if (isFavorite) {
            holder.icon_favorite.setImageResource(R.drawable.ic_favorite_fill);
        } else {
            holder.icon_favorite.setImageResource(R.drawable.ic_favorite);
        }

        holder.titleFavorite.setText(recipe.getTitle());
        holder.descriptionFavorite.setText(recipe.getTitle());

        String imageURL = recipe.getThemNailUrl();
        if (!imageURL.isBlank()) {
            Glide.with(holder.imageFavorite.getContext())
                    .load(BuildConfig.SAMPLE_API_KEY+ "/" + imageURL)
                    .into(holder.imageFavorite);
        }

        holder.click_area.setOnClickListener(view -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(recipe.getId());
            }
        });

        holder.icon_favorite.setOnClickListener(view -> {
            if (favoriteIconClickListener != null) {
                if (isFavorite) {
                    isFavorite = false;
                    // 좋아요 취소 기능
//                    recipeDao.delete(recipe.toRecipeEntity());
                    holder.icon_favorite.setImageResource(R.drawable.ic_favorite);
                } else {
                    isFavorite = true;
                    //좋아요 기능
                    holder.icon_favorite.setImageResource(R.drawable.ic_favorite_fill);
                }
                favoriteIconClickListener.onIconClick(recipe.getId(), true);
            }
        });
    }


    @Override
    public int getItemCount() {

        return  recipeList == null ? 0 : recipeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleFavorite;
        TextView descriptionFavorite;
        ImageView imageFavorite;
        ConstraintLayout main_layout;
        ConstraintLayout click_area;
        ImageView icon_favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFavorite = itemView.findViewById(R.id.image_favorite);
            titleFavorite = itemView.findViewById(R.id.title_favorite);
            descriptionFavorite = itemView.findViewById(R.id.description_favorite);
            main_layout = itemView.findViewById(R.id.main_layout);
            click_area = itemView.findViewById(R.id.click_area);
            icon_favorite = itemView.findViewById(R.id.icon_favorite);
        }
    }
}
