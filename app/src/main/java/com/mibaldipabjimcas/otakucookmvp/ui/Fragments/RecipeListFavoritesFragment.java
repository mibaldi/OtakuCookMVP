package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.features.MainActivity.MainActivityComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeListFavorites.RecipeListFavoritesPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Adapters.RecipesListAdapter;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeListFavoritesView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mikelbalducieldiaz on 17/7/16.
 */
public class RecipeListFavoritesFragment  extends BaseMVPFragment<RecipeListFavoritesPresenter,RecipeListFavoritesView> implements RecipeListFavoritesView{
    private MainActivityComponent component;
    private Unbinder unbind;

    @BindView(R.id.recipe_recyclerView)
    RecyclerView recipe_recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.noRecipes)
    RelativeLayout noRecipes;

    @Inject
    RecipesListAdapter recipesListAdapter;

    @Inject
    public RecipeListFavoritesFragment() {
        setRetainInstance(true);
    }

    public static RecipeListFavoritesFragment newInstance() {
        RecipeListFavoritesFragment fragment = new RecipeListFavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        component = getComponent(MainActivityComponent.class);
        component.inject(this);
        View view = inflater.inflate(R.layout.fragment_recipe_list_favorites,container,false);
        unbind = ButterKnife.bind(this,view);
        recipe_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @Override
    public RecipeListFavoritesPresenter createPresenter() {
        return component.recipeListFavoritesPresenter();
    }

    @Override
    public void showRecipeFavoriteList(List<Recipe> recipes) {
        recipesListAdapter.setDataAndListener(recipes, new RecipesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, Recipe recipe) {
                presenter.loadRecipe(recipe);
            }
        });
        recipe_recyclerView.setAdapter(recipesListAdapter);

    }

    @Override
    public void showNoRecipes(Boolean b) {
        if(b){
            noRecipes.setVisibility(View.VISIBLE);
        }else{
            noRecipes.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgressBar(Boolean b) {
        if(b){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNoConnectivity() {
        Snackbar.make(getView(), "No tienes conexión", Snackbar.LENGTH_SHORT).show();
    }
}
