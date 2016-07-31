package com.mibaldipabjimcas.otakucookmvp.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPFragment;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionPresenter;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.RecipeTaskListComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeTaskList.RecipeTaskListPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeDescriptionView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.RecipeTaskListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeTaskListFragment extends BaseMVPFragment<RecipeTaskListPresenter,RecipeTaskListView>  implements RecipeTaskListView{
    private RecipeTaskListComponent component;
    private Unbinder unbind;
    @BindView(R.id.taskImage)
    ImageView taskImage;
    @BindViews({ R.id.taskName, R.id.taskDescription, R.id.taskSeconds })
    List<TextView> taskFields;

    @BindView(R.id.layout_time)
    LinearLayout layoutTime;

    @Inject
    public RecipeTaskListFragment() {
        setRetainInstance(true);
    }
    public static RecipeTaskListFragment newInstance(Task task) {
        RecipeTaskListFragment fragment = new RecipeTaskListFragment();
        Bundle args = new Bundle();
        args.putParcelable("task",task);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Task task=getArguments().getParcelable("task");
        presenter.init(task);
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

    @Override
    public void showTaskImage(String photo) {
        Glide.with(getActivity()).load(photo).placeholder(R.drawable.default_recipe).into(taskImage);
    }

    @Override
    public void showTaskName(String name) {
        taskFields.get(0).setText(getString(R.string.taskName, name));
    }

    @Override
    public void showTaskTime(int minutes) {
        taskFields.get(2).setText(getString(R.string.minutesInTask, minutes));
    }

    @Override
    public void showTaskDescription(String description) {
        taskFields.get(1).setText(description);
    }

    @Override
    public void hideTime() {
        layoutTime.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar(Boolean b) {

    }

    @Override
    public void showNoConnectivity() {

    }
}
