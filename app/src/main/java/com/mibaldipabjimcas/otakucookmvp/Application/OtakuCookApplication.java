package com.mibaldipabjimcas.otakucookmvp.Application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.mibaldipabjimcas.otakucookmvp.BuildConfig;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class OtakuCookApplication extends Application {
    private OtakuCookApplicationComponent otakuCookComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        initDebugTools();
        otakuCookComponent=DaggerOtakuCookApplicationComponent.builder()
                .otakuCookApplicationModule(new OtakuCookApplicationModule(this)).build();
    }
    public OtakuCookApplicationComponent getInjector(){
        return otakuCookComponent;
    }
    private void initDebugTools() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
