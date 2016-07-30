package com.mibaldipabjimcas.otakucookmvp.features.Preferences;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import android.support.v7.widget.Toolbar;

import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplication;
import com.mibaldipabjimcas.otakucookmvp.Application.OtakuCookApplicationComponent;
import com.mibaldipabjimcas.otakucookmvp.R;
import com.mibaldipabjimcas.otakucookmvp.Utils.ThemeType;
import com.mibaldipabjimcas.otakucookmvp.Utils.ThemeUtils;

import javax.inject.Inject;

/**
 * Created by mikelbalducieldiaz on 30/7/16.
 */
public class PreferencesFragment extends PreferenceFragment{
    private Toolbar mToolbar;
    @Inject
    PreferencesManager preferenceManager;

    public PreferencesFragment(){}

    public void setToolbar(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getInjector().inject(this);
        addPreferencesFromResource(R.xml.preferences);
       // mToolbar=((PreferencesActivity)getActivity()).toolbar;
        final ListPreference listPreference = (ListPreference) findPreference("toolbarColor");
       listPreference.setValueIndex(preferenceManager.getSelectedTheme().ordinal());
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listPreference.setValueIndex(Integer.parseInt(newValue.toString()));
                preferenceManager.saveThemePreference(Integer.valueOf(newValue.toString()));
                applyTheme();
                return false;
            }
        });
    }
    protected OtakuCookApplicationComponent getInjector() {
        return ((OtakuCookApplication) getActivity().getApplication()).getInjector();
    }

    private void applyTheme() {
        ThemeType theme = preferenceManager.getSelectedTheme();
        ThemeUtils.applyThemeIntoStatusBar(getActivity(), theme);
       ThemeUtils.applyThemeIntoToolbar(getActivity(), theme, mToolbar);
    }
}
