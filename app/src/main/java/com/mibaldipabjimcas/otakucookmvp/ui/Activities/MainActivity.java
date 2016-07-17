package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.mibaldipabjimcas.otakucookmvp.Base.BaseActivity;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Main.DaggerMainComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainModule;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainPresenter;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeList.DaggerRecipeListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeList.RecipeListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeList.RecipeListModule;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeListFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements HasComponent<RecipeListComponent> {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MainComponent mainComponent;
    private RecipeListComponent recipeListComponent;
    private Unbinder unbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbind = ButterKnife.bind(this);
        this.initializeInjector();
        this.initializeActivity();
        setupViews();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }



    private void initializeActivity() {
        addFragment(R.id.content_main,new RecipeListFragment());
    }

    private void initializeInjector() {
       /* this.mainComponent = DaggerMainComponent.builder()
                .otakuCookApplicationComponent(getInjector())
                .mainModule(new MainModule())
                .build();*/
        this.recipeListComponent = DaggerRecipeListComponent.builder()
                .otakuCookApplicationComponent(getInjector())
                .recipeListModule(new RecipeListModule())
                .build();
    }

    /*public MainComponent getComponent(){
        return mainComponent;
    }*/
    public RecipeListComponent getComponent() {return  recipeListComponent;}
    public static Intent getCallingIntent(Context context){
        return new Intent(context,MainActivity.class);
    }
    private void setupViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setHomeAsUpIndicator(new DrawerArrowDrawable(toolbar.getContext()));
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.item1:
                Timber.d("item1");
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
