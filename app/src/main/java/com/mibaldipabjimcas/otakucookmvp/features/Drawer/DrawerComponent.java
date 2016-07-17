package com.mibaldipabjimcas.otakucookmvp.features.Drawer;

import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplicationComponent;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainModule;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainPresenter;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeList.RecipeListModule;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeList.RecipeListPresenter;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeListFavorites.RecipeListFavoritesModule;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeListFavorites.RecipeListFavoritesPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.MainActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Adapters.RecipesListAdapter;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeListFavoritesFragment;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = OtakuCookApplicationComponent.class,modules = {DrawerModule.class, MainModule.class, RecipeListModule.class, RecipeListFavoritesModule.class})
public interface DrawerComponent {

    void inject(MainActivity drawerActivity);
    void inject(MainFragment mainFragment);
    void inject(RecipeListFragment recipeListFragment);
    void inject(RecipeListFavoritesFragment recipeListFavoritesFragment);
    void inject(RecipesListAdapter recipesListAdapter);

    DrawerPresenter drawerPresenter();
    MainPresenter mainPresenter();
    RecipeListPresenter recipeListPresenter();
    RecipeListFavoritesPresenter recipeListFavoritesPresenter();
}
