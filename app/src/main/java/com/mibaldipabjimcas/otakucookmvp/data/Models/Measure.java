package com.mibaldipabjimcas.otakucookmvp.data.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels.MeasureFB;

/**
 * Created by mikelbalducieldiaz on 9/4/16.
 */
public class Measure implements Parcelable {
    public String measure;
    public  float quantity;
    public long recipeId;
    public long  id;
    public Ingredient ingredient;
    public long ingredientId;

    public Measure() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.measure);
        dest.writeFloat(this.quantity);
        dest.writeLong(this.recipeId);
        dest.writeLong(this.id);
        dest.writeParcelable(this.ingredient, flags);
        dest.writeLong(this.ingredientId);
    }

    protected Measure(Parcel in) {
        this.measure = in.readString();
        this.quantity = in.readFloat();
        this.recipeId = in.readLong();
        this.id = in.readLong();
        this.ingredient = in.readParcelable(Ingredient.class.getClassLoader());
        this.ingredientId = in.readLong();
    }

    public static final Creator<Measure> CREATOR = new Creator<Measure>() {
        @Override
        public Measure createFromParcel(Parcel source) {
            return new Measure(source);
        }

        @Override
        public Measure[] newArray(int size) {
            return new Measure[size];
        }
    };
    /*public static MeasureFB Measure2FB(Measure measure,long ingredientId){
        return new MeasureFB(measure,ingredientId);
    }
    public static  Measure FB2Measure(MeasureFB measureFB,Ingredient ingredient){
        Measure measure = new Measure();
        measure.ingredient = ingredient;
        measure.measure = measureFB.measure;
        measure.quantity = measureFB.quantity;
        return measure;
    }*/
}
