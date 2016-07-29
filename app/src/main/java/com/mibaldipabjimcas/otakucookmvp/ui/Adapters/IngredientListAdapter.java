package com.mibaldipabjimcas.otakucookmvp.ui.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Ingredient;
import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pabji on 04/04/2016.
 */
public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientListHolder> {

    private List<Measure> listItem;

    @Inject
    public IngredientListAdapter() {
    }

    @Override
    public IngredientListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_list, parent, false);
        return new IngredientListHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientListHolder holder, int position) {
        holder.bindItem(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listItem.size();
    }

    public void setData(List<Measure> ingredientList) {
        this.listItem = ingredientList;
        this.notifyDataSetChanged();
    }

    public class IngredientListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredientItemName)
        TextView ingredientItemName;

        @BindView(R.id.quantityMeasure)
        TextView quantityMeasure;

        public IngredientListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Measure measure) {
            ingredientItemName.setText(measure.ingredient.name);
            String ingredientMeasure = "";
            if(!measure.measure.equals("-")){
                ingredientMeasure = measure.measure;
            }
            quantityMeasure.setText(measure.quantity+" "+ingredientMeasure);
        }

    }

}
