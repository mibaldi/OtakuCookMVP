package com.mibaldipabjimcas.otakucookmvp.ui.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.mibaldipabjimcas.otakucookmvp.data.Models.Task;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeTaskListActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.RecipeTaskListFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mikelbalducieldiaz on 21/7/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public List<Task> taskList = new ArrayList<>();

    public ViewPagerAdapter(FragmentActivity activity, List<Task> taskList) {
        super(activity.getSupportFragmentManager());
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        this.taskList = taskList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return taskList.get(position).name;
    }

    @Override
    public Fragment getItem(int position) {

        return RecipeTaskListFragment.newInstance(taskList.get(position));
    }

    @Override
    public int getCount() {
        return taskList.size();
    }
}
