package com.mibaldipabjimcas.otakucookmvp.Base;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseActivityModule {
    private Context context;

    public BaseActivityModule(Context context) {
        this.context = context;
    }
    @Named("ActivityContext")
    @Provides
    public Context provideActivityContext(){
        return context;
    }
}
