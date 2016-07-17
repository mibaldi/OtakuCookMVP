package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseActivity;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.DaggerRecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionModule;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.DaggerRecipeTaskListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.RecipeTaskListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.RecipeTaskListModule;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;

public class RecipeTaskListActivity  extends BaseActivity implements HasComponent<RecipeTaskListComponent> {


    private RecipeTaskListComponent recipeTaskListComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_task_list);
        this.initializeInjector();
        this.initializeActivity();
    }

    private void initializeActivity() {
        addFragment(R.id.content_main,new MainFragment());
    }

    private void initializeInjector() {
        this.recipeTaskListComponent = DaggerRecipeTaskListComponent.builder()
                .otakuCookApplicationComponent(getInjector())
                .recipeTaskListModule(new RecipeTaskListModule())
                .build();
    }

    public RecipeTaskListComponent getComponent(){
        return recipeTaskListComponent;
    }
    public static Intent getCallingIntent(Context context){
        return new Intent(context,RecipeTaskListActivity.class);
    }
}
