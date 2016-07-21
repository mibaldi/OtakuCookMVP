package com.mibaldipabjimcas.otakucookmvp.features.Main;

import android.view.View;

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
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;

import javax.inject.Inject;

@PerActivity
public class MainPresenter extends BasePresenter<MainView> {

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    Navigator navigator;
    private String currentRecipe;
    private String previousRecipe;

    @Inject
    public MainPresenter(Navigator navigator) {
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

                    int numberChilds = (int) dataSnapshot.getChildrenCount();
                    if (numberChilds != 0) {
                        if (numberChilds > 1) {
                            getView().showRandomButton(View.VISIBLE);
                        }
                        randomRecipe(dataSnapshot.getChildren(),numberChilds);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    getView().showError(ErrorConstants.RECIPE_LIST_FAVORITE_ERROR);
                }
            });
        }
    }

    public void randomRecipe(Iterable<DataSnapshot> iterable,int numberChilds){
        int x = (int) (Math.random() * numberChilds);
        int count = 0;

        for (DataSnapshot child : iterable) {
            if (x == count) {
                currentRecipe = child.getKey();
                if (currentRecipe.equals(previousRecipe)){
                    randomRecipe(iterable,numberChilds);
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

        mRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Recipe r = dataSnapshot.getValue(Recipe.class);
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
