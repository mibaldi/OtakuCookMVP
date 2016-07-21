package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeDescriptionView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecipeDescriptionFragment extends BaseMVPFragment<RecipeDescriptionPresenter,RecipeDescriptionView>  implements RecipeDescriptionView{
    private RecipeDescriptionComponent component;
    @BindView(R.id.recipePhoto)
    ImageView imageView;

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
    public void showRecipeTime() {
        presenter.recipeTime();
    }

    @OnClick(R.id.fab)
    @Override
    public void recipeFavorite() {
        presenter.setRecipeFavorite();
    }

    @OnClick(R.id.btn_tasklist)
    @Override
    public void showRecipeTaskList() {
        presenter.showRecipeTaskList();
    }
}
