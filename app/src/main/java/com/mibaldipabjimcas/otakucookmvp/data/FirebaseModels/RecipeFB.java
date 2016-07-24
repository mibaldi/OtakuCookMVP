package com.mibaldipabjimcas.otakucookmvp.data.FirebaseModels;

import com.mibaldipabjimcas.otakucookmvp.data.Models.Recipe;

/**
 * Created by mikelbalducieldiaz on 21/7/16.
 */

public class RecipeFB {
    public  String name;
    public int portions;
    public String author;
    public int score;
    public String photo;

    public RecipeFB(){}

    public RecipeFB (Recipe recipe){
        this.name = recipe.name;
        this.portions = recipe.portions;
        this.author = recipe.author;
        this.score = recipe.score;
        this.photo = recipe.photo;
    }

}
