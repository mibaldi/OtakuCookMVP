package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.mibaldipabjimcas.otakucookmvp.Base.BaseActivity;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Main.DaggerMainComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainComponent;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainModule;
import com.mibaldipabjimcas.otakucookmvp.features.Main.MainPresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity implements HasComponent<MainComponent> {

    private MainComponent mainComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initializeInjector();
        this.initializeActivity();
    }

    private void initializeActivity() {
        addFragment(R.id.content_main,new MainFragment());
    }

    private void initializeInjector() {
        this.mainComponent = DaggerMainComponent.builder()
                .otakuCookApplicationComponent(getInjector())
                .mainModule(new MainModule())
                .build();
    }

    public MainComponent getComponent(){
        return mainComponent;
    }
    public static Intent getCallingIntent(Context context){
        return new Intent(context,MainActivity.class);
    }
}
