package com.mibaldipabjimcas.otakucookmvp.Navigation;

import android.content.Context;
import android.content.Intent;

import com.mibaldipabjimcas.otakucookmvp.ui.Activities.MainActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeDescriptionActivity;

import javax.inject.Inject;

public class Navigator {
    Context context;
    @Inject
    public Navigator(Context context){
        this.context = context;
    }

    public void openRecipeDescription(int id){
        if (context != null) {
            Intent intent = RecipeDescriptionActivity.getCallingIntent(context);
            intent.putExtra("recipeId",id);
            context.startActivity(intent);
        }
    }

}
