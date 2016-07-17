package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.features.Drawer.DrawerComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeList.RecipeListPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Adapters.RecipesListAdapter;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mikelbalducieldiaz on 17/7/16.
 */
public class RecipeListFragment extends BaseMVPFragment<RecipeListPresenter,RecipeListView> implements RecipeListView{
    private DrawerComponent component;
    private Unbinder unbind;
    @BindView(R.id.recipe_recyclerView)
    RecyclerView recipe_recyclerView;
    @Inject
    RecipesListAdapter recipesListAdapter;
    @Inject
    public RecipeListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        component = getComponent(DrawerComponent.class);
        component.inject(this);
        View view = inflater.inflate(R.layout.fragment_recipe_list,container,false);
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
    public RecipeListPresenter createPresenter() {
        return component.recipeListPresenter();
    }

    @Override
    public void showRecipeList(List<Recipe> recipeList) {
        recipesListAdapter.setDataAndListener(recipeList, new RecipesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, long id) {

                presenter.loadRecipe(id);
            }
        });
        recipe_recyclerView.setAdapter(recipesListAdapter);

    }

    public static RecipeListFragment newInstance() {
        RecipeListFragment fragment = new RecipeListFragment();
        return fragment;
    }
}
