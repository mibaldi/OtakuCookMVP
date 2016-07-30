package com.mibaldipabjimcas.otakucookmvp.features.Preferences;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mibaldipabjimcas.otakucookmvp.Base.BaseActivity;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.DaggerRecipeDescriptionComponent;
import com.mibaldipabjimcas.otakucookmvp.features.RecipeDescription.RecipeDescriptionModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mikelbalducieldiaz on 30/7/16.
 */

public class PreferencesActivity extends BaseActivity{

   @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        ButterKnife.bind(this);
        changeSupportActionBar(toolbar);

        toolbar.setTitle("Preferences");
        PreferencesFragment preferencesFragment = new PreferencesFragment();
        preferencesFragment.setToolbar(toolbar);
        getFragmentManager().beginTransaction().
                replace(R.id.contentPreferences, preferencesFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        applySelectedTheme(toolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }
    public static Intent getCallingIntent(Context context){
        return new Intent(context,PreferencesActivity.class);
    }
}
