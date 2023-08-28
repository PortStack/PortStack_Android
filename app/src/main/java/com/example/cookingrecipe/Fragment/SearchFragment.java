package com.example.cookingrecipe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingrecipe.Activity.DetailRecipeActivity;
import com.example.cookingrecipe.Adapter.RecipeListAdapter;

import com.example.cookingrecipe.Domain.DTO.RecipeDTO;
import com.example.cookingrecipe.Domain.DTO.RecipePageDTO;
import com.example.cookingrecipe.Domain.Model.Recipe;

import com.example.cookingrecipe.Retrofit.API.RecipeAPI;
import com.example.cookingrecipe.Retrofit.RetrofitClient;
import com.example.cookingrecipe.Room.AppDatabase;
import com.example.cookingrecipe.Room.DAO.RecipeDao;
import com.example.cookingrecipe.Room.Entity.RecipeEntity;
import com.example.cookingrecipe.Util.TokenUtil;
import com.example.cookingrecipe.databinding.FragmentSearchBinding;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    private static final String ARG_SEARCH_DATA = "search_data";
    FragmentSearchBinding binding;
    String searchData;
    private RecyclerView.Adapter adapter;
    SearchView searchView;
    List<Recipe> recipeList;
    RecyclerView recyclerSearch;
    Boolean isLocalDataLoaded = false;
    Boolean isFirebaseDataLoaded = false;
    private CountDownLatch countDownLatch;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static SearchFragment newInstance(String searchData) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_DATA, searchData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Bundle args = getArguments();


        searchView = binding.search;
        recyclerSearch = binding.recylerSearch;

        requestRecipe();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecipe(newText);
                return true;
            }
        });

        if (args != null) {
            searchData = args.getString(ARG_SEARCH_DATA);
            searchView.setQuery(searchData, false);
            Log.e("Search Data",searchData);
            // searchData를 사용하여 작업 처리
        }
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
         searchView.requestFocus();
    }

    public void searchRecipe(String text) {
        RecipeAPI recipeAPI = RetrofitClient.getClient().create(RecipeAPI.class);
        recipeAPI.search(text).enqueue(new Callback<RecipePageDTO>() {

            @Override
            public void onResponse(@NonNull Call<RecipePageDTO> call, @NonNull Response<RecipePageDTO> response) {
                if(response.isSuccessful()){
                    RecipePageDTO recipes = response.body();

                    Log.d("RecipeRequest", String.valueOf(recipes));

                    List<RecipeDTO.Request> recipeList = recipes.getItems();

                    showRecipe(recipeList);

                }
            }

            @Override
            public void onFailure(Call<RecipePageDTO> call, Throwable t) {
                Log.d("Recipe", TokenUtil.getRefreshToken("실패"));
                t.printStackTrace();
            }

        });
    }

    public void showRecipe(List<RecipeDTO.Request> recipeList) {


        if (recipeList != null) {
            Set<RecipeDTO.Request> showSet = new HashSet<>();
            for (RecipeDTO.Request recipe : recipeList) {
                if (showSet.size() < 12) {
                    showSet.add(recipe);
                } else {
                    break;
                }
            }

            List<RecipeDTO.Request> showList = new ArrayList<>(showSet);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerSearch = binding.recylerSearch;
            recyclerSearch.setLayoutManager(linearLayoutManager);

            RecipeListAdapter recipeListAdapter = new RecipeListAdapter(showList);
            recipeListAdapter.setOnItemClickListener(recipeId -> {
                Intent intent = new Intent(getActivity(), DetailRecipeActivity.class);
                intent.putExtra("recipeId", recipeId);
                startActivity(intent);
            });
            recipeListAdapter.setOnFavoriteIconClickListener((position, recipeId) -> {
                // 즐겨찾기 아이콘 클릭 시 동작 정의
            });

            adapter = recipeListAdapter;
            recyclerSearch.setAdapter(adapter);
        }
    }

    public void requestRecipe(){
        RecipeAPI recipeAPI = RetrofitClient.getClient().create(RecipeAPI.class);
        recipeAPI.getRecipes(0,10,"id,DESC").enqueue(new Callback<RecipePageDTO>() {

            @Override
            public void onResponse(@NonNull Call<RecipePageDTO> call, @NonNull Response<RecipePageDTO> response) {
                if(response.isSuccessful()){
                    RecipePageDTO recipes = response.body();

                    Log.d("RecipeRequest", String.valueOf(recipes));

                    List<RecipeDTO.Request> recipeList = recipes.getItems();

                    showRecipe(recipeList);

                }
            }

            @Override
            public void onFailure(Call<RecipePageDTO> call, Throwable t) {
                Log.d("Recipe", TokenUtil.getRefreshToken("실패"));
                t.printStackTrace();
            }

        });
    }

    private String removeAccent(String s) {
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String strFormD = Normalizer.normalize(s, Normalizer.Form.NFD);
        return pattern.matcher(strFormD).replaceAll("").replace('đ', 'd').replace('Đ', 'D');
    }
}