package com.mibaldipabjimcas.otakucookmvp.features.Main;

import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplicationComponent;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Fragments.MainFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = OtakuCookApplicationComponent.class,modules = MainModule.class)
public interface MainComponent {

    void inject (MainFragment mainFragment);

    MainPresenter presenter();
}
