package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseActivity;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.DaggerRecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionModule;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.DaggerRecipeTaskListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.RecipeTaskListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.RecipeTaskListModule;
import com.mibaldipabjimcas.otakucookmvp.ui.Adapters.ViewPagerAdapter;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeTaskListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeTaskListActivity  extends BaseActivity implements HasComponent<RecipeTaskListComponent> {


    private RecipeTaskListComponent recipeTaskListComponent;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_task_list);
        ButterKnife.bind(this);
        ArrayList<Task> taskList = getIntent().getParcelableArrayListExtra("taskList");
        this.initializeInjector();
        this.initializeActivity(taskList);
    }

    private void initializeActivity(ArrayList<Task> taskArrayList) {
        viewPager.setAdapter(new ViewPagerAdapter(this,taskArrayList));
       // addFragment(R.id.content_main, RecipeTaskListFragment.newInstance(taskArrayList));
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
