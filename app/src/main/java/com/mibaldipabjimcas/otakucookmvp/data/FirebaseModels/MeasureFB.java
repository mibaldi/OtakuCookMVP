package com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels;

import com.mibaldipabjimcas.otakucookmvp.data.Models.Measure;

/**
 * Created by mikelbalducieldiaz on 21/7/16.
 */

public class MeasureFB {
    public String measure;
    public  float quantity;
    public long ingredientId;

    public MeasureFB (Measure measure,long ingredientId){
        this.measure = measure.measure;
        this.quantity = measure.quantity;
        this.ingredientId = ingredientId;
    }
}
