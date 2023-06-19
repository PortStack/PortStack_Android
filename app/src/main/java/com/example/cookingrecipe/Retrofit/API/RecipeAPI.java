package com.example.cookingrecipe.Retrofit.API;


import com.example.cookingrecipe.Domain.DTO.RecipeDTO;
import com.example.cookingrecipe.Domain.DTO.RecipePageDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeAPI {

    @GET("/recipe")
    Call<RecipePageDTO> getRecipes(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );

}

