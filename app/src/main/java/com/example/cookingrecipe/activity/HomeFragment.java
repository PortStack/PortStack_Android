package com.example.cookingrecipe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.Adapter.CategoryAdapter;
import com.example.cookingrecipe.Adapter.RecipeTodayAdapter;
import com.example.cookingrecipe.Domain.DTO.RecipeDTO;
import com.example.cookingrecipe.Domain.Model.Recipe;
import com.example.cookingrecipe.Domain.Model.Type;
import com.example.cookingrecipe.Domain.Network.FirebaseRecipe;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Retrofit.API.RecipeAPI;
import com.example.cookingrecipe.Retrofit.RetrofitClient;
import com.example.cookingrecipe.databinding.FragmentHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategoryList;
    private RecyclerView recyclerViewRecipeTodayList;

    private ConstraintLayout searchButton;
    FragmentHomeBinding binding;

    private NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerViewCategory();
        recyclerViewRecipeToday();



        searchButton = binding.searchBtn;
        searchButton.setOnClickListener(v -> openSearchFragment());
        return view;
    }

    public void openSearchFragment() {
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
    }
    private void recyclerViewRecipeToday() {
        new FirebaseRecipe().getAllRecipe(recipeList -> {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewRecipeTodayList = binding.recyclerViewToday;
            recyclerViewRecipeTodayList.setLayoutManager(linearLayoutManager);

            RecipeTodayAdapter recipeTodayAdapter = new RecipeTodayAdapter(recipeList);
            recipeTodayAdapter.setOnItemClickListener(recipeId -> {
                Intent intent = new Intent(getActivity(), DetailRecipeActivity.class);
                intent.putExtra("recipeId", recipeId);
                startActivity(intent);
            });
            adapter = recipeTodayAdapter;
            recyclerViewRecipeTodayList.setAdapter(adapter);
        });

    }

    private void recyclerViewCategory() {

        ArrayList<Type> typeList = new ArrayList<>();
        typeList.add(new Type(0, "item1", "dough"));
        typeList.add(new Type(1, "item2", "dough"));
        typeList.add(new Type(2, "item3", "dough"));
        typeList.add(new Type(3, "item4", "dough"));
        typeList.add(new Type(4, "item5", "dough"));
        typeList.add(new Type(5, "item6", "dough"));
        typeList.add(new Type(6, "item7", "dough"));
        typeList.add(new Type(7, "item8", "dough"));
        typeList.add(new Type(8, "item9", "dough"));
        typeList.add(new Type(9, "item10", "dough"));
        int totalItems = typeList.size();
        int spanCount = (totalItems % 2 == 0) ? totalItems / 2 : (totalItems / 2) + 1;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);

        recyclerViewCategoryList = binding.recyclerViewCategory;
        recyclerViewCategoryList.setLayoutManager(gridLayoutManager);

        adapter = new CategoryAdapter(typeList);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void Recipe(){
        RecipeAPI recipeAPI = RetrofitClient.getClient(null).create(RecipeAPI.class);
        Call<RecipeDTO.Request> call = recipeAPI.getRecipes(0,10,"id,DESC");
    }
}