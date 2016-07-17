package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.features.Drawer.DrawerComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeListFavorites.RecipeListFavoritesPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeListFavoritesView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mikelbalducieldiaz on 17/7/16.
 */
public class RecipeListFavoritesFragment  extends BaseMVPFragment<RecipeListFavoritesPresenter,RecipeListFavoritesView> implements RecipeListFavoritesView{
    private DrawerComponent component;
    private Unbinder unbind;

    @Inject
    public RecipeListFavoritesFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        component = getComponent(DrawerComponent.class);
        component.inject(this);
        View view = inflater.inflate(R.layout.fragment_recipe_list_favorites,container,false);
        unbind = ButterKnife.bind(this,view);
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

}
