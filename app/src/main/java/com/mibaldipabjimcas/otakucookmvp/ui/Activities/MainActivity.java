package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPActivity;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Drawer.DaggerDrawerComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Drawer.DrawerComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Drawer.DrawerPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeListFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.DrawerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class MainActivity extends BaseMVPActivity<DrawerPresenter,DrawerView> implements DrawerView, HasComponent<DrawerComponent> {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private DrawerComponent drawerComponent;

    private Unbinder unbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbind = ButterKnife.bind(this);
        this.initializeActivity();
        setupViews();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }

    @NonNull
    @Override
    public DrawerPresenter createPresenter() {
        return drawerComponent.drawerPresenter();
    }


    private void initializeActivity() {
        selectFragment(MainFragment.newInstance());
    }

    private void initializeInjector() {
       this.drawerComponent = DaggerDrawerComponent.builder()
                .otakuCookApplicationComponent(getInjector())
                .build();
    }

    public DrawerComponent getComponent(){
        return drawerComponent;
    }

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
                selectFragment(MainFragment.newInstance());
                return true;
            case R.id.item2:
                Timber.d("item2");
                selectFragment(RecipeListFragment.newInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectFragment(Fragment fragment){
        addFragment(R.id.content_main,fragment);
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
