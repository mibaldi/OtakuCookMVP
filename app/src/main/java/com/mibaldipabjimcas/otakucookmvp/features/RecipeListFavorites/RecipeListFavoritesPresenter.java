package com.mibaldipabjimcas.otakucookmvp.features.RecipeListFavorites;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Constants.ErrorConstants;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.ApiClientRepository;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.LoginActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeListFavoritesView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@PerActivity
public class RecipeListFavoritesPresenter extends BasePresenter<RecipeListFavoritesView> {


    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    private List<Recipe> recipes = new ArrayList<>();
    private boolean firebaseError = false;
    Navigator navigator;

    @Inject
    public RecipeListFavoritesPresenter(Navigator navigator) {
        this.navigator = navigator;
    }

    public void init(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refRoot  = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();

        if (mAuth != null) {
            mRef = refRoot.child(mAuth.getCurrentUser().getUid()).child("favorites");
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        Recipe r = ds.getValue(Recipe.class);
                        recipes.add(r);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    firebaseError = true;
                    getView().showError(ErrorConstants.RECIPE_LIST_FAVORITE_ERROR);
                }
            });
            if(!firebaseError) {
                getView().showRecipeFavoriteList(recipes);
            }
        }
    }

    public void loadRecipe(Recipe recipe){
        navigator.openRecipeDescription(recipe);
    }

}
