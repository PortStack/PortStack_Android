package com.example.cookingrecipe.Retrofit.API;


import com.example.cookingrecipe.Domain.DTO.CommentDTO;
import com.example.cookingrecipe.Domain.DTO.CookOrdersDTO;
import com.example.cookingrecipe.Domain.DTO.RecipeDTO;
import com.example.cookingrecipe.Domain.DTO.RecipePageDTO;
import com.example.cookingrecipe.Domain.DTO.TagDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeAPI {

    @GET("/recipe")
    Call<RecipePageDTO> getRecipes(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );

    @GET("/recipe/category")
    Call<TagDTO.List> getCategory(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );

    @GET("/recipe/read/{id}")
    Call<RecipeDTO.Request> getRecipeDetail(
            @Path("id") int page
    );

    @Multipart
    @POST("/recipe/new")
    Call<ResponseBody> setNewRecipes(@Part("dto") RequestBody cookOrdersDTO, @Part List<MultipartBody.Part> orderImage, @Part List<MultipartBody.Part> themNail);

    @GET("/main/search")
    Call<RecipePageDTO> search(@Query("searchText") String searchText);

    @POST("/recipe/comment/new/{recipeId}")
    Call<Integer> addComment(@Body CommentDTO dto, @Path("recipeId") int recipeId);

    @GET("/recipe/comment/{recipeId}/read")
    Call<List<CommentDTO>> getComments(@Path("recipeId") int recipeId);

}

