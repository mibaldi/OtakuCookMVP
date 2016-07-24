package com.mibaldipabjimcas.otakucookmvp.Services.Retrofit2;


import com.mibaldipabjimcas.otakucookmvp.data.Models.Ingredient;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPointInterface  {

    /*@GET("videos/{title}")
    Call<MainActivity.Respuesta> getNews(@Path("title") String title);

    @GET("news")
    Call<MainActivity.ListRespuesta> groupList();*/

    @GET("/recipes")
    Call<List<Recipe>> recipes();
    @GET("recipes/{id}")
    Call<Recipe> getRecipe(@Path("id") long id);
    @GET("/ingredients")
    Call<List<Ingredient>> ingredients();
    @GET("/recipesIngredients")
    Call<List<Recipe>> getPossiblesRecipes(@Query("ingredientes") String ingredientes);
    @GET("/ingredients/category/{category}")
    Call<List<Ingredient>> getCategoryIngredients(@Path("category") String category,@Query("ids") String ids);

}
