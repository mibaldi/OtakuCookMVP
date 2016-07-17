package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionPresenter;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.RecipeTaskListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.RecipeTaskListPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeDescriptionView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeTaskListView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeTaskListFragment extends BaseMVPFragment<RecipeTaskListPresenter,RecipeTaskListView>  implements RecipeTaskListView{
    private RecipeTaskListComponent component;
    private Unbinder unbind;

    @Inject
    public RecipeTaskListFragment() {
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

        component = getComponent(RecipeTaskListComponent.class);
        component.inject(this);
        View view = inflater.inflate(R.layout.fragment_recipe_task_list,container,false);
        unbind = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    @Override
    public RecipeTaskListPresenter createPresenter() {
        return component.presenter();
    }
}
