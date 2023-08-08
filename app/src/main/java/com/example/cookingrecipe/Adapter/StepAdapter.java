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
import com.example.cookingrecipe.Domain.DTO.CookOrdersDTO;
import com.example.cookingrecipe.Domain.DTO.RecipeDTO;
import com.example.cookingrecipe.Domain.Model.Recipe;
import com.example.cookingrecipe.Domain.Model.RecipeOrder;
import com.example.cookingrecipe.Domain.Model.Step;
import com.example.cookingrecipe.R;


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    RecipeDTO.Request recipe;

    public StepAdapter(RecipeDTO.Request recipe) {
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_step, parent, false);
        return new StepAdapter.ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.ViewHolder holder, int position) {

        if (recipe.getCookOrderList() != null) {
            RecipeOrder step = recipe.getCookOrderList().get(holder.getAdapterPosition());
            holder.step_title.setText("단계" + step.getSequence());
            holder.step_text.setText(step.getContent());

            String imageUrl = step.getOrderImageName();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                System.out.println("image");
                Glide.with(holder.step_image.getContext())
                        .load("http://211.109.43.213:8081/images" + imageUrl)
                        .into(holder.step_image);
            } else {
                System.out.println("none");
                holder.step_image.setVisibility(View.GONE); // Ẩn ImageView nếu không có ảnh

            }
        }
    }

    @Override
    public int getItemCount() {
        return recipe.getCookOrderList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView step_title;
        TextView step_text;
        ImageView step_image;
        ConstraintLayout main_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            step_title = itemView.findViewById(R.id.step_title);
            step_text = itemView.findViewById(R.id.ingredient_text);
            step_image = itemView.findViewById(R.id.step_image);
            main_layout = itemView.findViewById(R.id.main_layout);

        }
    }


}
