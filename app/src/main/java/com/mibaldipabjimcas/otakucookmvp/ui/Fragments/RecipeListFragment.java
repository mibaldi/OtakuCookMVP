package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.Services.Connectivity.Connectivity;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.features.MainActivity.MainActivityComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeList.RecipeListPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Adapters.RecipesListAdapter;
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
public class RecipeListFragment extends BaseMVPFragment<RecipeListPresenter, RecipeListView> implements RecipeListView, SearchView.OnQueryTextListener {
    private MainActivityComponent component;
    private Unbinder unbind;
    private List<Recipe> recipeList = new ArrayList<>();
    @BindView(R.id.recipe_recyclerView)
    RecyclerView recipe_recyclerView;

    @BindView(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    RecipesListAdapter recipesListAdapter;
    private MenuItem myActionMenuItem;
    private SearchView searchView;
    private ProgressDialog progressDialog;

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
        setHasOptionsMenu(true);
        progressDialog =  new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        presenter.init(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        component = getComponent(MainActivityComponent.class);
        component.inject(this);
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        unbind = ButterKnife.bind(this, view);
        recipe_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadSwipeRefreshLayout();

        return view;
    }

    private void loadSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Connectivity.isNetworkAvailable(getView().getContext())) {
                    presenter.loadServerService();
                } else {
                    Snackbar.make(getView(), R.string.no_connectivity, Snackbar.LENGTH_LONG).show();
                }
            }
        });
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
        this.recipeList=recipeList;
        recipesListAdapter.setDataAndListener(recipeList, new RecipesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, Recipe recipe) {

                presenter.loadRecipe(recipe);
            }
        });
        recipe_recyclerView.setAdapter(recipesListAdapter);

    }

    @Override
    public void swipeRefresh(Boolean b) {
        mSwipeRefreshLayout.setRefreshing(b);
    }

    @Override
    public void showProgressDialog(int message) {
            progressDialog.setMessage(getString(message));
            progressDialog.show();
    }

    @Override
    public void cancelProgressDialog() {
        progressDialog.setCancelable(true);
        progressDialog.cancel();
    }

    @Override
    public void showNoConnectivity() {
        Snackbar.make(getView(), R.string.no_connectivity, Snackbar.LENGTH_SHORT).show();
    }

    public static RecipeListFragment newInstance() {
        RecipeListFragment fragment = new RecipeListFragment();
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);

        myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(myActionMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Recipe> filteredModelList= presenter.filter(recipeList,newText);
        recipesListAdapter.setDataAndListener(filteredModelList, new RecipesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, Recipe recipe) {

                presenter.loadRecipe(recipe);
            }
        });

        return false;
    }
}
