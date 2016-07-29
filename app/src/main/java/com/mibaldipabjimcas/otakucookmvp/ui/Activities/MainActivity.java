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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPActivity;
import com.mibaldipabjimcas.otakucookmvp.BuildConfig;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.MainActivity.DaggerMainActivityComponent;
import com.mibaldipabjimcas.otakucookmvp.features.MainActivity.MainActivityComponent;
import com.mibaldipabjimcas.otakucookmvp.features.MainActivity.MainActivityPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeListFavoritesFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeListFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainActivityView;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class MainActivity extends BaseMVPActivity<MainActivityPresenter,MainActivityView> implements MainActivityView, HasComponent<MainActivityComponent> {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MainActivityComponent mainActivityComponent;

    private Unbinder unbind;
    private NavigationView navigationView;
    private View navigationViewHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbind = ButterKnife.bind(this);
        if(savedInstanceState == null)
            this.initializeActivity();

        setupViews();
        presenter.init(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;

    }

    @NonNull
    @Override
    public MainActivityPresenter createPresenter() {
        return mainActivityComponent.drawerPresenter();
    }


    private void initializeActivity() {
        selectFragment(MainFragment.newInstance());
    }

    private void initializeInjector() {
       this.mainActivityComponent = DaggerMainActivityComponent.builder()
                .otakuCookApplicationComponent(getInjector())
                .build();
    }

    public MainActivityComponent getComponent(){
        return mainActivityComponent;
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
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if(BuildConfig.SHOW_PREMIUM_ACTIONS){
            navigationView.inflateMenu(R.menu.main_navigation);
        }else{
            navigationView.inflateMenu(R.menu.main_navigation_free);
        }

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
            case R.id.item3:
                Timber.d("item3");
                selectFragment(RecipeListFavoritesFragment.newInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectFragment(Fragment fragment){
        addFragment(R.id.content_main,fragment);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void showUserName(String name) {
        navigationViewHeaderView=navigationView.getHeaderView(0);
        TextView textView=(TextView)navigationViewHeaderView.findViewById(R.id.login_status);
        textView.setText(name);
    }

    @Override
    public void showUserAvatar(String photo) {
        ImageView imageView=(ImageView) navigationViewHeaderView.findViewById(R.id.user_image);
        Glide.with(this).load(photo).placeholder(R.mipmap.ic_launcher).into(imageView);
    }

    @Override
    public void showProgressBar(Boolean b) {

    }
}
