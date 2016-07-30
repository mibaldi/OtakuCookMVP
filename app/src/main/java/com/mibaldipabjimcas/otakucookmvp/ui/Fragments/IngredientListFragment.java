package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Ingredient;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.features.IngredientList.IngredientListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.IngredientList.IngredientListPresenter;
import com.mibaldipabjimcas.otakucookmvp.features.MainActivity.MainActivityComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeList.RecipeListPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.IngredientListActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeDescriptionActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Adapters.IngredientListAdapter;
import com.mibaldipabjimcas.otakucookmvp.ui.Adapters.RecipesListAdapter;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.IngredientListView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mikelbalducieldiaz on 17/7/16.
 */
public class IngredientListFragment extends BaseMVPFragment<IngredientListPresenter, IngredientListView> implements IngredientListView{
    private IngredientListComponent component;
    private Unbinder unbind;
    @BindView(R.id.recipe_recyclerView)
    RecyclerView ingredient_recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    IngredientListAdapter ingredientListAdapter;

    @Inject
    public IngredientListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Measure> ingredients=getArguments().getParcelableArrayList("measures");
        presenter.init(ingredients);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        component = getComponent(IngredientListComponent.class);
        component.inject(this);
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        unbind = ButterKnife.bind(this, view);
        ingredient_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @Override
    public IngredientListPresenter createPresenter() {
        return component.presenter();
    }

    @Override
    public void showIngredientList(ArrayList<Measure> ingredientList) {
        ingredientListAdapter.setData(ingredientList);
        ingredient_recyclerView.setAdapter(ingredientListAdapter);

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

    }

    public static IngredientListFragment newInstance(ArrayList<Measure> measures) {
        IngredientListFragment fragment = new IngredientListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("measures",measures);
        fragment.setArguments(args);
        return fragment;
    }
}
