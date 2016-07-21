package com.mibaldipabjimcas.otakucookmvp.ui.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pabji on 04/04/2016.
 */
public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipeListHolder> {

    private List<Recipe> listItem;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClickListener(View view, Recipe recipe);
    }

    @Inject
    public RecipesListAdapter() {
    }

    @Override
    public RecipeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_list, parent, false);
        return new RecipeListHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(RecipeListHolder holder, int position) {
        holder.bindItem(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listItem.size();
    }

    public void setDataAndListener(List<Recipe> recipeList, OnItemClickListener listener) {
        this.listItem = recipeList;
        this.listener = listener;
        this.notifyDataSetChanged();
    }


    public class RecipeListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipeItemName)
        TextView recipeItemName;
        public Recipe recipe;
        public OnItemClickListener listener;


        public RecipeListHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        public void bindItem(Recipe recipe) {
            this.recipe = recipe;
            recipeItemName.setText(recipe.name);
        }

        @OnClick(R.id.recipeItem)
        public void onClickItem() {
            this.listener.onItemClickListener(itemView,recipe);
        }
    }

}
