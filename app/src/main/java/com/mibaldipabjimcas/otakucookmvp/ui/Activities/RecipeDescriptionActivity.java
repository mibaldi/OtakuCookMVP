package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseActivity;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Main.DaggerMainComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainModule;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.DaggerRecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionModule;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;

public class RecipeDescriptionActivity extends BaseActivity implements HasComponent<RecipeDescriptionComponent> {

    private RecipeDescriptionComponent recipeDescriptionComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description);
        this.initializeInjector();
        this.initializeActivity();
    }

    private void initializeActivity() {
        addFragment(R.id.content_main,new MainFragment());
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
