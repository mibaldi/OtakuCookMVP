package com.mibaldipabjimcas.otakucookmvp.Navigation;

import android.content.Context;
import android.content.Intent;

import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.MainActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeDescriptionActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeTaskListActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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


}
