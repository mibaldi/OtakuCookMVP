package com.mibaldipabjimcas.otakucookmvp.features.RecipeListFavorites;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Constants.ErrorConstants;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.RecipeFB;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.ApiClientRepository;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.LoginActivity;
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


    private FirebaseAuth mAuth;

    private List<Recipe> recipes = new ArrayList<>();
    Navigator navigator;
    private DatabaseReference refUserRecipe;
    private DatabaseReference refRecipes;
    /*private DatabaseReference refMeasures;
    private DatabaseReference refTasks;
    private DatabaseReference refIngredients;

    private Recipe recipe;*/

    @Inject
    public RecipeListFavoritesPresenter(Navigator navigator) {
        this.navigator = navigator;
    }

    private void initRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        refUserRecipe  = database.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("favorites");
        refRecipes  = database.getReference("Recipes");
        //refMeasures  = database.getReference("Measures");
        //refTasks  = database.getReference("Tasks");
        //refIngredients  = database.getReference("Ingredients");
    }


    public void init(){

        initRef();

        if (mAuth != null) {
            getRecipeListFB();
        }
    }

    private void getRecipeListFB() {
        if(recipes.size() > 0){
            getView().showRecipeFavoriteList(recipes);
        }else {
            recipes.clear();
            refUserRecipe.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        getRecipeFirebase(snapshot.getKey(), dataSnapshot.getChildrenCount());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    getView().showError(ErrorConstants.FIREBASE_ERROR);
                }
            });
        }
    }

    private void getRecipeFirebase(final String key, final long childrenCount) {
        refRecipes.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RecipeFB recipe = dataSnapshot.getValue(RecipeFB.class);
                recipes.add(new Recipe(key,recipe));
                if(recipes.size() == childrenCount)
                    getView().showRecipeFavoriteList(recipes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    public void loadRecipe(final Recipe recipe){
        this.navigator.openRecipeDescription(recipe);
        /*this.recipe = recipe;

        refRecipes.child(String.valueOf(recipe.id)).child("tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    getTaskFirebase(snapshot.getKey(),dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });*/
    }

    /*private void getTaskFirebase(String key, long childrenCount) {
        refTasks.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                recipe.tasks.add(task);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }*/

}
