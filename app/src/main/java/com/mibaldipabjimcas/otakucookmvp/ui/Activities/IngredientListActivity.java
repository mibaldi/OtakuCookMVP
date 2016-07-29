package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseActivity;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.IngredientList.DaggerIngredientListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.IngredientList.IngredientListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.DaggerRecipeTaskListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.RecipeTaskListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.RecipeTaskListModule;
import com.mibaldipabjimcas.otakucookmvp.ui.Adapters.ViewPagerAdapter;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.IngredientListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientListActivity extends BaseActivity implements HasComponent<IngredientListComponent> {

    private IngredientListComponent ingredientListComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);
        ButterKnife.bind(this);
        ArrayList<Measure> ingredients = getIntent().getParcelableArrayListExtra("measures");
        this.initializeInjector();
        this.initializeActivity(ingredients);
    }

    private void initializeActivity(ArrayList<Measure> ingredients) {
       addFragment(R.id.content, IngredientListFragment.newInstance(ingredients));
    }

    private void initializeInjector() {
        this.ingredientListComponent = DaggerIngredientListComponent.builder()
                .otakuCookApplicationComponent(getInjector())
                .build();
    }

    public IngredientListComponent getComponent(){
        return ingredientListComponent;
    }
    public static Intent getCallingIntent(Context context){
        return new Intent(context,IngredientListActivity.class);
    }
}
