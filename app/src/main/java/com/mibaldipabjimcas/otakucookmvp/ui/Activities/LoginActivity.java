package com.mibaldipabjimcas.otakucookmvp.ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseMVPActivity;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.di.HasComponent;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.DaggerLoginFirebaseComponent;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.LoginFirebaseComponent;
import com.mibaldipabjimcas.otakucookmvp.features.LoginFirebase.LoginFirebasePresenter;
import com.mibaldipabjimcas.otakucookmvp.ui.Views.LoginFirebaseView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends BaseMVPActivity<LoginFirebasePresenter,LoginFirebaseView> implements LoginFirebaseView, HasComponent<LoginFirebaseComponent> {

    private LoginFirebaseComponent component;

    private Unbinder unbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbind = ButterKnife.bind(this);
        presenter.init(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbind.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode,data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }



    @NonNull
    @Override
    public LoginFirebasePresenter createPresenter() {
        return component.presenter();
    }

    @OnClick(R.id.sign_in_button)
    public void sign_in(){
        presenter.signIn();
    }

    @OnClick(R.id.sign_out_button)
    public void sign_out(){
        presenter.signOut();
    }

    @OnClick(R.id.disconnect_button)
    public void disconnect(){
        presenter.disconnect();
    }

    private void initializeInjector() {
       this.component = DaggerLoginFirebaseComponent.builder()
                .otakuCookApplicationComponent(getInjector())
                .build();
    }

    public LoginFirebaseComponent getComponent(){
        return component;
    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context,LoginActivity.class);
    }

    @Override
    public void showProgressBar(Boolean b) {

    }
}
