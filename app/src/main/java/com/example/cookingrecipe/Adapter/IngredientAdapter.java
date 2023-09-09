package com.example.cookingrecipe.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.Domain.DTO.IngredientDTO;
import com.example.cookingrecipe.Domain.DTO.RecipeDTO;
import com.example.cookingrecipe.Domain.Model.Ingredient;
import com.example.cookingrecipe.Domain.Model.Recipe;
import com.example.cookingrecipe.R;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    RecipeDTO.Request recipe;

    public IngredientAdapter(RecipeDTO.Request recipe) {
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_ingredient, parent, false);
        return new ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        System.out.println(recipe.getIngredientList().get(0).getNames());
        IngredientDTO ingredient = recipe.getIngredientList().get(holder.getAdapterPosition());
        holder.ingredient_text.setText(ingredient.getCounts() + " " + ingredient.getUnits() + " " + ingredient.getNames());
    }

    @Override
    public int getItemCount() {
        return recipe.getIngredientList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient_text;
        ConstraintLayout main_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient_text = itemView.findViewById(R.id.ingredient_text);
            main_layout = itemView.findViewById(R.id.main_layout);
        }
    }
}
