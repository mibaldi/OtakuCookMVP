package com.mibaldipabjimcas.otakucookmvp.Application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class OtakuCookApplication extends Application {
    private OtakuCookApplicationComponent otakuCookComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        otakuCookComponent=DaggerOtakuCookApplicationComponent.builder()
                .otakuCookApplicationModule(new OtakuCookApplicationModule(this)).build();
    }
    public OtakuCookApplicationComponent getInjector(){
        return otakuCookComponent;
    }
}
