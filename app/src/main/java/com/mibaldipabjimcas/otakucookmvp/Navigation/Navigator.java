package com.mibaldipabjimcas.otakucookmvp.Navigation;

import android.content.Context;

import javax.inject.Inject;

public class Navigator {
    Context context;
    @Inject
    public Navigator(Context context){
        this.context = context;
    }

}
