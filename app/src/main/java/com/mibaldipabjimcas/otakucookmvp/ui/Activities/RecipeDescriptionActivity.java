package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseActivity;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.DaggerRecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionModule;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeDescriptionFragment;

public class RecipeDescriptionActivity extends BaseActivity implements HasComponent<RecipeDescriptionComponent> {

    private RecipeDescriptionComponent recipeDescriptionComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description);
        Recipe recipe = getIntent().getParcelableExtra("recipe");
        this.initializeInjector();
        this.initializeActivity(recipe);
    }

    private void initializeActivity(Recipe recipe) {
        addFragment(R.id.content_main,RecipeDescriptionFragment.newInstance(recipe));
    }

    private void initializeInjector() {
        this.recipeDescriptionComponent = DaggerRecipeDescriptionComponent.builder()
                .otakuCookApplicationComponent(getInjector())
                .recipeDescriptionModule(new RecipeDescriptionModule())
                .build();
    }

    public RecipeDescriptionComponent getComponent(){
        return recipeDescriptionComponent;
    }
    public static Intent getCallingIntent(Context context){
        return new Intent(context,RecipeDescriptionActivity.class);
    }
}
