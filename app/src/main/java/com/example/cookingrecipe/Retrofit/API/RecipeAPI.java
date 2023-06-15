package com.example.cookingrecipe.Retrofit.API;


import com.example.cookingrecipe.Domain.DTO.RecipeDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeAPI {

    @GET("/recipe")
    Call<RecipeDTO.Request> getRecipes(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );

}

