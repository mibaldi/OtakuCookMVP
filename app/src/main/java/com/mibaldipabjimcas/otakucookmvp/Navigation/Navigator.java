package com.mibaldipabjimcas.otakucookmvp.Navigation;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.features.Preferences.PreferencesActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.IngredientListActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.LoginActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.MainActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeDescriptionActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeTaskListActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.FavoriteDialogFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {
    Context context;
    @Inject
    public Navigator(Context context){
        this.context = context;
    }

    public void openRecipeDescription(Recipe recipe){
        if (context != null) {
            Intent intent = RecipeDescriptionActivity.getCallingIntent(context);
            intent.putExtra("recipe",recipe);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void openRecipeTaskList(ArrayList<Task> taskList){
        if (context != null) {
            Intent intent = RecipeTaskListActivity.getCallingIntent(context);
            intent.putParcelableArrayListExtra("taskList",taskList);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void openMain(){
        if (context != null) {
            Intent intent = MainActivity.getCallingIntent(context);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }
    public void goToLogin(){
        if (context != null) {
            Intent intent = LoginActivity.getCallingIntent(context);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }


    public void openRecipeIngredientList(ArrayList<Measure> measures) {
        if (context != null) {
            Intent intent = IngredientListActivity.getCallingIntent(context);
            intent.putParcelableArrayListExtra("measures",measures);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void openFavoriteDialog(Fragment fragment, Boolean favorite) {
        FavoriteDialogFragment dialogFragment = FavoriteDialogFragment.newInstance(favorite);
        dialogFragment.setTargetFragment(fragment, 1);
        dialogFragment.show(fragment.getFragmentManager(), "dialog");
    }

    public void openPreferences(){
        if (context != null) {
            Intent intent = PreferencesActivity.getCallingIntent(context);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
