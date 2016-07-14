package com.mibaldipabjimcas.otakucookmvp.features.Main;

import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;

import javax.inject.Inject;

@PerActivity
public class MainPresenter extends BasePresenter<MainView> {
    @Inject
    public MainPresenter() {
    }
}
