package com.mibaldipabjimcas.otakucookmvp.features.Main;

import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.BuildConfig;
import com.mibaldipabjimcas.otakucookmvp.Constants.ErrorConstants;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.RecipeFB;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;

import javax.inject.Inject;

import timber.log.Timber;

@PerActivity
public class MainPresenter extends BasePresenter<MainView> {

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    Navigator navigator;
    private String currentRecipe;
    private String previousRecipe;
    private DatabaseReference refRecipes;
    private Iterable<DataSnapshot> recipes;
    private int numberRecipes;

    @Inject
    public MainPresenter(Navigator navigator) {
        this.navigator = navigator;
    }

    public void init(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refRoot  = database.getReference("Users");
        refRecipes = database.getReference("Recipes");
        mAuth = FirebaseAuth.getInstance();

        if (mAuth != null) {

            if(BuildConfig.SHOW_PREMIUM_ACTIONS){
                mRef = refRoot.child(mAuth.getCurrentUser().getUid()).child("favorites");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        recipes = dataSnapshot.getChildren();
                        numberRecipes = (int) dataSnapshot.getChildrenCount();
                        if (numberRecipes != 0) {
                            if (numberRecipes > 1) {
                                getView().showRandomButton(View.VISIBLE);
                            }
                            randomRecipe();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        getView().showError(ErrorConstants.RECIPE_LIST_FAVORITE_ERROR);
                    }
                });
        }else{
                //TODO
                //Caso no premium
                getView().showRecipeName("No premium");
            }
        }
    }

    public void randomRecipe(){
        Timber.d("Random");
        //TODO
        int x = (int) (Math.random() * numberRecipes);
        int count = 0;

        for (DataSnapshot child : recipes) {
            if (x == count) {
                currentRecipe = child.getKey();
                if (currentRecipe.equals(previousRecipe)){
                    randomRecipe();
                }else {
                    printRecipe(child.getKey());
                }
                break;
            }
            count++;
        }
    }

    private void printRecipe(String key) {
        previousRecipe = key;

        refRecipes.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RecipeFB r = dataSnapshot.getValue(RecipeFB.class);
                getView().showRecipeName(r.name);
                getView().showRecipeImage(r.photo);
                getView().showRatingBar(r.score);
                getView().showRecipeAuthor(r.author);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().showError(ErrorConstants.RECIPE_FAVORITE_ERROR);
            }
        });
    }
}
