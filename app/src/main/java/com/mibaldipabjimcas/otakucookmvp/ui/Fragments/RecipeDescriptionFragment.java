package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeDescriptionView;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

public class RecipeDescriptionFragment extends BaseMVPFragment<RecipeDescriptionPresenter,RecipeDescriptionView>  implements RecipeDescriptionView{
    private RecipeDescriptionComponent component;
    @BindView(R.id.recipePhoto)
    ImageView imageView;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
        args.putParcelable("recipe",recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Recipe recipe=getArguments().getParcelable("recipe");
        presenter.init(recipe);
        setSizeAppBarLayout();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        /*int time = presenter.calculateTime() * 1000;
        presenter.generateAlarm(getActivity(),new Long(time));
        Timber.d("tiempo de la receta: "+ time);
        Timber.d("tiempo de lanzamiento "+Calendar.getInstance().getTime().toString());*/

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        component = getComponent(RecipeDescriptionComponent.class);
        component.inject(this);
        View view = inflater.inflate(R.layout.fragment_recipe_description,container,false);
        unbind = ButterKnife.bind(this,view);
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

    public void setSizeAppBarLayout(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)appBarLayout.getLayoutParams();
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
            params.width = params.MATCH_PARENT;
            appBarLayout.setLayoutParams(params);
        }
    }


    @Override
    public void showRecipeImage(String photo) {
        Glide.with(getActivity()).load(photo).placeholder(R.mipmap.ic_launcher).into(imageView);
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

    @OnClick(R.id.bt_time)
    @Override
    public void showRecipeTime(Button button) {
        presenter.recipeTime();
        button.setVisibility(View.INVISIBLE);
        presenter.generateAlarm(getActivity(),presenter.calculateTime()*1000);
    }

    @OnClick(R.id.fab)
    @Override
    public void recipeFavorite() {
        presenter.setRecipeFavorite();
    }

    @OnClick(R.id.bt_tasks)
    @Override
    public void showRecipeTaskList() {
        presenter.showRecipeTaskList();
    }

    @Override
    public void changeFavoriteIcon(boolean b) {
        if(b){
            Snackbar.make(getView(),"Receta guardada como favorita",Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(getView(),"Receta eliminada de favoritos",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showRecipePortions(int portions) {

    }

    @Override
    public void showProgressBar(Boolean b) {
        if(b){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
}
