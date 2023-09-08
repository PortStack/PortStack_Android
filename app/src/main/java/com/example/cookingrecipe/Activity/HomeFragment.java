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
import com.example.cookingrecipe.Domain.DTO.TagDTO;
import com.example.cookingrecipe.Domain.DTO.UserDTO;
import com.example.cookingrecipe.Domain.Model.Recipe;
import com.example.cookingrecipe.Domain.Model.Type;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Retrofit.API.RecipeAPI;
import com.example.cookingrecipe.Retrofit.RetrofitClient;
import com.example.cookingrecipe.databinding.FragmentHomeBinding;
import com.example.cookingrecipe.Util.AuthConfig;
import com.example.cookingrecipe.Util.TokenUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    RecipePageDTO recipes;
    TagDTO.List tags;

    private NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        getRecipe(0,10);
        getCategory(0,10);


        searchButton = binding.searchBtn;
        searchButton.setOnClickListener(v -> openSearchFragment(""));
        return view;
    }

    public void openSearchFragment(String searchInput) {
        if (getActivity() instanceof MainActivity && searchInput!= null) {
            ((MainActivity) getActivity()).setSearchData(searchInput);
        }
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
    }
    private void recyclerViewRecipeToday(List<RecipeDTO.Request> recipeList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRecipeTodayList = binding.recyclerViewToday;
        recyclerViewRecipeTodayList.setLayoutManager(linearLayoutManager);


        // 어댑터와 연결
        RecipeTodayAdapter recipeTodayAdapter = new RecipeTodayAdapter(recipeList);
        recipeTodayAdapter.setOnItemClickListener(recipeId -> {
            Intent intent = new Intent(getActivity(), DetailRecipeActivity.class);
            intent.putExtra("recipeId", recipeId);
            startActivity(intent);
        });

        adapter = recipeTodayAdapter;
        recyclerViewRecipeTodayList.setAdapter(adapter);
    }

    //카테고리 뷰(이거 수정해서 서버에서 받아오도록 해야함
    private void recyclerViewCategory(List<TagDTO.Request> tags) {


        ArrayList<Type> typeList = IntStream.range(0, tags.size())
                .mapToObj(i -> new Type(i, tags.get(i).getTag(), "dough"))
                .collect(Collectors.toCollection(ArrayList::new));

        int totalItems = typeList.size();
        int spanCount = (totalItems % 2 == 0) ? totalItems / 2 : (totalItems / 2) + 1;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);

        recyclerViewCategoryList = binding.recyclerViewCategory;
        recyclerViewCategoryList.setLayoutManager(gridLayoutManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(typeList);
        categoryAdapter.setOnItemClickListener(tag -> {
            openSearchFragment(tags.get(tag).getTag());
        });


        adapter = categoryAdapter;
        recyclerViewCategoryList.setAdapter(adapter);
    }

    //카테고리 뷰(이거 수정해서 서버에서 받아오도록 해야함
    private void recyclerViewCategoryTest() {

        ArrayList<Type> typeList = new ArrayList<>();

        typeList.add(new Type(0,"item1","dough"));
        typeList.add(new Type(1,"item1","dough"));
        typeList.add(new Type(2,"item1","dough"));
        typeList.add(new Type(3,"item1","dough"));

        int totalItems = typeList.size();
        int spanCount = (totalItems % 2 == 0) ? totalItems / 2 : (totalItems / 2) + 1;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);

        recyclerViewCategoryList = binding.recyclerViewCategory;
        recyclerViewCategoryList.setLayoutManager(gridLayoutManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(typeList);
        categoryAdapter.setOnItemClickListener(tag -> {
            openSearchFragment(typeList.get(tag).getTitle());
        });


        adapter = categoryAdapter;
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void getRecipe(int page, int size){

        RecipeAPI recipeAPI = RetrofitClient.getClient().create(RecipeAPI.class);
        recipeAPI.getRecipes(page,size,"id,DESC",AuthConfig.getUserName(getContext())).enqueue(new Callback<RecipePageDTO>() {

            @Override
            public void onResponse(@NonNull Call<RecipePageDTO> call, @NonNull Response<RecipePageDTO> response) {
                if(response.isSuccessful()){
                    recipes = response.body();

                    Log.d("RecipeRequest", String.valueOf(recipes));

                    List<RecipeDTO.Request> recipeList = recipes.getItems();

                    recyclerViewRecipeToday(recipeList);

                }
            }

            @Override
            public void onFailure(Call<RecipePageDTO> call, Throwable t) {
                Log.d("RecipeRequest","error");

                t.printStackTrace();
            }

        });
    }

    private void getCategory(int page, int size){

        Log.d("TagService", "test");

        RecipeAPI recipeAPI = RetrofitClient.getClient().create(RecipeAPI.class);
        recipeAPI.getCategory(page,size,"id,DESC").enqueue(new Callback<TagDTO.List>() {

            @Override
            public void onResponse(@NonNull Call<TagDTO.List> call, @NonNull Response<TagDTO.List> response) {
                if(response.isSuccessful()){
                    try{
                        tags = response.body();

                        List<TagDTO.Request> tagList = tags.getItems();
                        Log.d("TagService", tagList.get(0).getTag());
                        recyclerViewCategory(tagList);
                    }catch(Exception e){
                        Log.e("TagService",e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<TagDTO.List> call, Throwable t) {
                Log.d("TagService",TokenUtil.getRefreshToken("실패"));
                t.printStackTrace();
            }

        });
    }
}