package com.mibaldipabjimcas.otakucookmvp.features.Main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.Base.DataListener;
import com.mibaldipabjimcas.otakucookmvp.BuildConfig;
import com.mibaldipabjimcas.otakucookmvp.Constants.ErrorConstants;
import com.mibaldipabjimcas.otakucookmvp.Navigation.Navigator;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.Services.Firebase.FirebaseRepository;
import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.RecipeFB;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;

import javax.inject.Inject;

import timber.log.Timber;

@PerActivity
public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    FirebaseRepository firebaseRepository;

    Navigator navigator;
    private String currentRecipe;
    private String previousRecipe;
    private Iterable<DataSnapshot> recipes;
    private int numberRecipes;
    private boolean existRandomButtom;
    private Context context;

    @Inject
    public MainPresenter(Navigator navigator) {
        this.navigator = navigator;
    }

    public void init(Context context){

        this.context = context;
        if (firebaseRepository.getAuth() != null) {

            if(BuildConfig.SHOW_PREMIUM_ACTIONS){

                if(previousRecipe != null){
                    printRecipe(previousRecipe);
                }else{
                    getView().showProgressDialog(R.string.loading_recipe);
                    getRandomRecipe();
                }

        }else{
                noPremium();
            }
        }
    }

    public void getRandomRecipe(){
        firebaseRepository.getFavorites(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipes = dataSnapshot.getChildren();
                numberRecipes = (int) dataSnapshot.getChildrenCount();
                if (numberRecipes != 0) {
                    if (numberRecipes > 1) {
                        existRandomButtom = true;
                    }
                    randomRecipe();
                }else{
                    printNoFavorites();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().cancelProgressDialog();
                getView().showError(ErrorConstants.RECIPE_LIST_FAVORITE_ERROR);
            }
        });
    }

    private void printNoFavorites() {
        getView().cancelProgressDialog();
        getView().showRecipeName(context.getString(R.string.no_favorites));
        getView().showDefaultImage();
        getView().showRandomButton(false);
        getView().showRecipeAuthor("");
        getView().showRatingBar(0);
    }

    public void randomRecipe(){
        Timber.d(previousRecipe);

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

    private void printRecipe(final String key) {
        previousRecipe = key;

        firebaseRepository.getRecipeBasic(key, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getView().cancelProgressDialog();
                RecipeFB r = dataSnapshot.getValue(RecipeFB.class);
                getView().showRecipeName(r.name);
                getView().showRecipeImage(r.photo);
                getView().showRatingBar(r.score);
                getView().showRecipeAuthor(r.author);
                if(existRandomButtom)
                    getView().showRandomButton(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getView().cancelProgressDialog();
                getView().showError(ErrorConstants.FIREBASE_ERROR);
            }
        });
    }

    public void noPremium(){
        getView().showRecipeName(context.getString(R.string.free_main_fragment));
        getView().showDefaultImage();
    }

    public void openRecipeDescription(){
        getView().showProgressDialog(R.string.loading_recipe);
        firebaseRepository.getRecipeComplete(previousRecipe, new DataListener<Recipe>() {
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
    }
}

