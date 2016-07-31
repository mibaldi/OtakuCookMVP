package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeDescriptionActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeDescriptionView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecipeDescriptionFragment extends BaseMVPFragment<RecipeDescriptionPresenter, RecipeDescriptionView> implements RecipeDescriptionView {
    private RecipeDescriptionComponent component;
    @BindView(R.id.recipePhoto)
    ImageView imageView;

    @BindView(R.id.layout_time)
    View layout_view;

    @BindView(R.id.image_time)
    ImageView imageTime;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.portions)
    TextView portions;

    @BindView(R.id.recipeName)
    TextView recipeName;

    @BindView(R.id.recipeAuthor)
    TextView recipeAuthor;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.fab)
    FloatingActionButton favorite;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder unbind;

    @Inject
    public RecipeDescriptionFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static RecipeDescriptionFragment newInstance(Recipe recipe) {
        RecipeDescriptionFragment fragment = new RecipeDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable("recipe", recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Recipe recipe = getArguments().getParcelable("recipe");
        presenter.init(recipe,getActivity());
        setSizeAppBarLayout();

        ((RecipeDescriptionActivity)getActivity()).changeSupportActionBar(toolbar);
        getActivity().setTitle("");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        component = getComponent(RecipeDescriptionComponent.class);
        component.inject(this);
        View view = inflater.inflate(R.layout.fragment_recipe_description, container, false);
        unbind = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @Override
    public RecipeDescriptionPresenter createPresenter() {
        return component.presenter();
    }

    public void setSizeAppBarLayout() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            params.width = params.MATCH_PARENT;
            appBarLayout.setLayoutParams(params);
        }
    }


    @Override
    public void showRecipeImage(String photo) {
        Glide.with(getActivity()).load(photo).placeholder(R.drawable.default_recipe).into(imageView);
    }

    @Override
    public void showRecipeName(String name) {
        recipeName.setText(name);
    }

    @Override
    public void showRecipeRating(int score) {
        ratingBar.setRating(score);
    }

    @Override
    public void showRecipeAuthor(String author) {
        recipeAuthor.setText(author);
    }

    @OnClick(R.id.bt_ingredients)
    @Override
    public void showRecipeIngredients() {
        presenter.showRecipeIngredientList();
    }


    @Override
    public void showRecipeTime(String timeString) {
        time.setText(timeString);
    }

    @OnClick(R.id.layout_time)
    @Override
    public void startRecipeTime() {
        presenter.openTimeDialog(this);
    }

    @OnClick(R.id.fab)
    @Override
    public void recipeFavorite() {
       presenter.openFavoriteDialog(this);
    }

    @Override
    public void setLayoutDisabled() {
        layout_view.setEnabled(false);
    }

    @Override
    public void setImageTimeStart() {
        imageTime.setImageResource(R.drawable.timeon);
    }

    @OnClick(R.id.bt_tasks)
    @Override
    public void showRecipeTaskList() {
        presenter.showRecipeTaskList();
    }


    @Override
    public void changeFavoriteIcon(boolean b) {
        if (b) {
            favorite.setImageDrawable(ResourcesCompat.getDrawable(getResources(),android.R.drawable.star_big_on,null));
        } else {
            favorite.setImageDrawable(ResourcesCompat.getDrawable(getResources(),android.R.drawable.star_big_off,null));
        }
    }

    @Override
    public void showRecipePortions(int number) {
        portions.setText(String.format("Receta para %d personas",number));
    }

    @Override
    public void hideFavoriteIcon() {
        favorite.setVisibility(View.GONE);
    }

    @Override
    public Drawable getDrawableImage() {

        Drawable drawable=imageView.getDrawable();
        return drawable;
    }

    @Override
    public void setFavorite(Boolean b) {
        changeFavoriteIcon(b);
        if(b){
            Snackbar.make(getView(), R.string.saved_favorite_recipe, Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(getView(), R.string.deleted_favorite_recipe, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgressBar(Boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNoConnectivity() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_share){
            presenter.openSharedDialog(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
