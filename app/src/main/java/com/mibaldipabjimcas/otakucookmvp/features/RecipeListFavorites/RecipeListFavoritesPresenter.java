package com.mibaldipabjimcas.otakucookmvp.features.RecipeListFavorites;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Base.DataListener;
import com.mibaldipabjimcas.otakucookmvp.Constants.ErrorConstants;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.Services.Connectivity.Connectivity;
import com.mibaldipabjimcas.otakucookmvp.Services.Firebase.FirebaseRepository;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.RecipeFB;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.ApiClientRepository;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.LoginActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeListFavoritesFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeListFavoritesView;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@PerActivity
public class RecipeListFavoritesPresenter extends BasePresenter<RecipeListFavoritesView> {

    @Inject
    FirebaseRepository firebaseRepository;

    private List<Recipe> recipes = new ArrayList<>();
    Navigator navigator;
    private Context context;

    @Inject
    public RecipeListFavoritesPresenter(Navigator navigator) {
        this.navigator = navigator;
    }

    public void init(Context context){
        this.context = context;
        if(Connectivity.isNetworkAvailable(context)) {
            if (firebaseRepository.getAuth() != null) {
                getRecipeListFB();
            }
        }else{
            getView().showNoConnectivity();
        }
    }

    private void getRecipeListFB() {
        if(recipes.size() > 0){
            getView().showRecipeFavoriteList(recipes);
        }else {
            recipes.clear();
            getView().showProgressDialog(R.string.loading_recipes);
            firebaseRepository.getRecipeFavorites(new DataListener<List<Recipe>>() {
                @Override
                public void onSuccess(List<Recipe> data) {
                    if(data.isEmpty()) {
                        getView().showNoRecipes();
                    }
                    getView().cancelProgressDialog();
                    getView().showRecipeFavoriteList(data);
                }

                @Override
                public void onError(int error) {
                    getView().showError(error);
                    getView().cancelProgressDialog();
                }
            });
        }
    }

    public void loadRecipe(final Recipe recipe) {
        if (Connectivity.isNetworkAvailable(context)) {
            getView().showProgressDialog(R.string.loading_recipe);
            firebaseRepository.getRecipeComplete(String.valueOf(recipe.id), new DataListener<Recipe>() {
                @Override
                public void onSuccess(Recipe data) {
                    getView().cancelProgressDialog();
                    navigator.openRecipeDescription(data);
                }

                @Override
                public void onError(int error) {
                    getView().showError(error);
                    getView().cancelProgressDialog();
                }
            });
        } else {
            getView().showNoConnectivity();
        }
    }
}
