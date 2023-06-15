package com.example.cookingrecipe.activity;


import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cookingrecipe.Adapter.IngredientAdapter;
import com.example.cookingrecipe.Adapter.StepAdapter;
import com.example.cookingrecipe.Domain.Model.Ingredient;
import com.example.cookingrecipe.Domain.Model.Recipe;
import com.example.cookingrecipe.Domain.Model.Step;
import com.example.cookingrecipe.Domain.Network.FirebaseRecipe;
import com.example.cookingrecipe.Domain.Network.NetworkHelper;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Room.AppDatabase;
import com.example.cookingrecipe.Room.DAO.RecipeDao;
import com.example.cookingrecipe.Room.Entity.RecipeEntity;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Arrays;
import java.util.List;

public class DetailRecipeActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewIngredients;
    private RecyclerView recyclerViewSteps;
    private TextView titleTextView;
    private TextView servingTextView;
    private TextView timeTextView;
    private String recipeId;
    private boolean isFavorite;
    private RecipeEntity recipeEntity;
    private ImageView recipe_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);
        recipeId = getIntent().getStringExtra("recipeId");

        AppDatabase db = AppDatabase.getInstance(this);
        RecipeDao recipeDao = db.recipeDao();
        isFavorite = recipeDao.getById(recipeId) != null;

        toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setOnMenuItemClickListener(this::onNavigationItemSelected);


        if (NetworkHelper.isNetworkConnected(this)) {
            new FirebaseRecipe().getRecipeById(recipeId, recipe -> {
                showDetailRecipe(recipe);
            });
        } else {
            Recipe recipe = recipeDao.getById(recipeId).toRecipe();
            showDetailRecipe(recipe);
        }
    }

    public void showDetailRecipe(Recipe recipe) {
        recipe_image = findViewById(R.id.recipe_image);
        String imageURL = recipe.getImage();
        if (!imageURL.isBlank()) {
            Glide.with(recipe_image.getContext())
                    .load(imageURL)
                    .into(recipe_image);
        }
        titleTextView = findViewById(R.id.recipe_title);
        titleTextView.setText(recipe.getTitle());
        toolbar.setTitle(recipe.getTitle());
        servingTextView = findViewById(R.id.recipe_serving);
        servingTextView.setText(recipe.getServings());
        timeTextView = findViewById(R.id.recipe_time);
        timeTextView.setText(String.valueOf(recipe.getTime()));
        recyclerViewIngredient(recipe);
        recyclerViewStep(recipe);
        recipeEntity = recipe.toRecipeEntity();
    }

    private boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favorite) {
            toggleFavorite();
            return true;
        } else if (item.getItemId() == R.id.share) {

            String subject = recipeEntity.getTitle();
            String s = Arrays.toString(recipeEntity.getTags().toArray(new String[0]));
            String text = "레시피 작성:\n" // Nội dung của công thức
                    + "제목: " + recipeEntity.getTitle() + "\n"
                    + "글: " + recipeEntity.getDescription() + "\n"
                    + "사람수: " + recipeEntity.getServings() + "\n"
                    + "시간: " + recipeEntity.getTime() + " 분\n"
                    + "종류: " + s + "\n"
                    + "재료:\n" + getIngredientsText(recipeEntity.getIngredients()) + "\n"
                    + "조리단계:\n" + getStepsText(recipeEntity.getSteps());

            // Tạo Intent chia sẻ
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);

            startActivity(Intent.createChooser(shareIntent, "레시피 공유"));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private String getIngredientsText(List<Ingredient> ingredients) {
        StringBuilder sb = new StringBuilder();
        for (Ingredient ingredient : ingredients) {
            sb.append("- ")
                    .append(ingredient.getName())
                    .append(": ")
                    .append(ingredient.getAmount())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }
        return sb.toString();
    }

    private String getStepsText(List<Step> steps) {
        StringBuilder sb = new StringBuilder();
        for (Step step : steps) {
            sb.append("단계 ")
                    .append(step.getStep_order())
                    .append(": ")
                    .append("\n")
                    .append(step.getDescription())
                    .append("\n");
        }

        return sb.toString();
    }

    private void toggleFavorite() {
        if (isFavorite) {

            AppDatabase db = AppDatabase.getInstance(this);
            RecipeDao recipeDao = db.recipeDao();
            recipeDao.delete(recipeDao.getById(recipeId));
            toolbar.getMenu().findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_white);
            isFavorite = false;
        } else {

            AppDatabase db = AppDatabase.getInstance(this);
            RecipeDao recipeDao = db.recipeDao();
            recipeDao.insert(recipeEntity);
            toolbar.getMenu().findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_fill);
            isFavorite = true;
        }
    }

    private void recyclerViewStep(Recipe recipe) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSteps = findViewById(R.id.step);
        recyclerViewSteps.setLayoutManager(linearLayoutManager);
        adapter = new StepAdapter(recipe);
        recyclerViewSteps.setAdapter(adapter);

    }

    private void recyclerViewIngredient(Recipe recipe) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewIngredients = findViewById(R.id.ingredient);
        recyclerViewIngredients.setLayoutManager(linearLayoutManager);

        adapter = new IngredientAdapter(recipe);
        recyclerViewIngredients.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_material_toolbar, menu);
        if (isFavorite) {
            toolbar.getMenu().findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_fill);
        }
        return true;
    }
}