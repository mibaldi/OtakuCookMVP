package com.mibaldipabjimcas.otakucookmvp.data.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikelbalducieldiaz on 17/7/16.
 */
public class Recipe implements Parcelable{
    public long id;
    public long idServer;
    public  String name;
    public int portions;
    public Boolean favorite;
    public String author;
    public int score;
    public String photo;
    public List<Measure> measureIngredients;
    public ArrayList<Task> tasks;
    public List<Ingredient> ingredients;

    public List<Measure> getMeasures() {
        return measureIngredients;
    }

    public void setMeasures(List<Measure> measures) {
        this.measureIngredients = measures;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Recipe() {
        ingredients = new ArrayList<Ingredient>();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.idServer);
        dest.writeString(this.name);
        dest.writeInt(this.portions);
        dest.writeValue(this.favorite);
        dest.writeString(this.author);
        dest.writeInt(this.score);
        dest.writeString(this.photo);
        dest.writeTypedList(measureIngredients);
        dest.writeTypedList(tasks);
        dest.writeTypedList(ingredients);
    }

    protected Recipe(Parcel in) {
        this.id = in.readLong();
        this.idServer = in.readLong();
        this.name = in.readString();
        this.portions = in.readInt();
        this.favorite = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.author = in.readString();
        this.score = in.readInt();
        this.photo = in.readString();
        this.measureIngredients = in.createTypedArrayList(Measure.CREATOR);
        this.tasks = in.createTypedArrayList(Task.CREATOR);
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
