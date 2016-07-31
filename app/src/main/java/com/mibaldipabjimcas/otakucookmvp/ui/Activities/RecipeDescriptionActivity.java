package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseActivity;
import com.mibaldipabjimcas.otakucookmvp.BuildConfig;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.DaggerRecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionModule;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeDescriptionFragment;

public class RecipeDescriptionActivity extends BaseActivity implements HasComponent<RecipeDescriptionComponent> {

    public static int FAVORITE_OK = 1;
    public static int SHARED_OK = 2;
    public static int TIME_OK = 3;

    private RecipeDescriptionComponent recipeDescriptionComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description);
        Recipe recipe = getIntent().getParcelableExtra("recipe");
        this.initializeInjector();
        this.initializeActivity(recipe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (BuildConfig.SHOW_PREMIUM_ACTIONS){
            getMenuInflater().inflate(R.menu.menu_shared, menu);
        }
        return true;
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
