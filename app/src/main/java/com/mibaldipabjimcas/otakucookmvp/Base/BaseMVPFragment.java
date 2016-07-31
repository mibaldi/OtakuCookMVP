package com.mibaldipabjimcas.otakucookmvp.Base;


import android.os.Bundle;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;

public abstract class BaseMVPFragment<P extends BasePresenter<V>, V extends BaseView> extends MvpFragment<V, P> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    public void showError(int error) {
        //Toast.makeText(getActivity(), "Error: " + error, Toast.LENGTH_SHORT).show();
    }

}
