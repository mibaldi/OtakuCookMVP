package com.mibaldipabjimcas.otakucookmvp.features.Drawer;

import com.mibaldipabjimcas.otakucookmvp.Base.BasePresenter;
import com.mibaldipabjimcas.otakucookmvp.di.PerActivity;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.DrawerView;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.MainView;

import javax.inject.Inject;

@PerActivity
public class DrawerPresenter extends BasePresenter<DrawerView> {
    @Inject
    public DrawerPresenter() {
    }


}
