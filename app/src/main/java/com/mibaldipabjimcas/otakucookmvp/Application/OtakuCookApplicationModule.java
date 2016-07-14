package com.mibaldipabjimcas.otakucookmvp.Application;


import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class OtakuCookApplicationModule {
    private final Context context;
    public OtakuCookApplicationModule(Context context){
        this.context = context;
    }
    @Named("ApplicationContext")
    @Provides
    @Singleton
    Context providedApplicationContext(){
        return this.context;
    }
}
