package com.mibaldipabjimcas.otakucookmvp.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplication;
import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplicationComponent;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.ui.Activities.RecipeDescriptionActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity  extends AppCompatActivity{


        //@Inject
        //Navigator navigator;

       // @BindView(R.id.toolbar)
        //Toolbar toolbar;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.getInjector().inject(this);
        }

       /* protected void configSupportActionBar() {

            ButterKnife.bind(this);
            changeSupportActionBar(toolbar);
        }*/


       public void changeSupportActionBar(Toolbar toolbar){
           setSupportActionBar(toolbar);

            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }

        protected OtakuCookApplicationComponent getInjector(){
            return ((OtakuCookApplication) getApplication()).getInjector();
        }

        protected void addFragment(int containerViewId, Fragment fragment) {
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(containerViewId, fragment);
            fragmentTransaction.commit();
        }

        protected BaseActivityModule getActivityModule(){
            return new BaseActivityModule(this);
        }

      /*  public int getStatusBarHeight() {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }*/

        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }

        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                finish();
            }
            return super.onOptionsItemSelected(item);
        }

}
