package com.example.cookingrecipe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.Adapter.CategoryAdapter;
import com.example.cookingrecipe.Adapter.RecipeTodayAdapter;
import com.example.cookingrecipe.Domain.DTO.RecipeDTO;
import com.example.cookingrecipe.Domain.DTO.RecipePageDTO;
import com.example.cookingrecipe.Domain.DTO.UserDTO;
import com.example.cookingrecipe.Domain.Model.Recipe;
import com.example.cookingrecipe.Domain.Model.Type;
import com.example.cookingrecipe.Domain.Network.FirebaseRecipe;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Retrofit.API.RecipeAPI;
import com.example.cookingrecipe.Retrofit.RetrofitClient;
import com.example.cookingrecipe.databinding.FragmentHomeBinding;
import com.example.cookingrecipe.Util.AuthConfig;
import com.example.cookingrecipe.Util.TokenUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

        Recipe(0,10);

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

    private void Recipe(int page, int size){
        RecipeAPI recipeAPI = RetrofitClient.getClient().create(RecipeAPI.class);
        recipeAPI.getRecipes(page,size,"id,DESC").enqueue(new Callback<RecipePageDTO>() {

            @Override
            public void onResponse(@NonNull Call<RecipePageDTO> call, @NonNull Response<RecipePageDTO> response) {
                if(response.isSuccessful()){
                    RecipePageDTO recipes = response.body();

//                    Log.d("RecipeTest", recipes.getItems().get(0).getTitle().toString());
                }
            }

            @Override
            public void onFailure(Call<RecipePageDTO> call, Throwable t) {
                Log.d("Test",TokenUtil.getRefreshToken("실패"));
                t.printStackTrace();
            }

        });
    }
}