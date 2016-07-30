package com.mibaldipabjimcas.otakucookmvp.Services.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.mibaldipabjimcas.otakucookmvp.Utils.ThemeType;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mikelbalducieldiaz on 30/7/16.
 */

@Singleton
public class PreferencesManager {

    private static final String SHARED_PREFS_FILE = "MimoPrefs";
    private static final String THEME_PREF_KEY = "toolbarColor";
    private static PreferencesManager instance;
    private SharedPreferences sharedPreferences;
    private Context mContext;
    private Integer themePreference;



   /* public static PreferencesManager getInstance() {
        if(instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }*/

    @Inject
    public PreferencesManager() {}

    public static @Nullable
    PreferencesManager setContext(Context context) {
        if(instance == null) {
            return null;
        }
        instance.mContext = context;
        instance.sharedPreferences = instance.getSharedPreferences();
        return instance;
    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
    }

    public void saveThemePreference(int themePreference) {
        this.themePreference = themePreference;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(THEME_PREF_KEY, themePreference);
        editor.apply();
    }

    public ThemeType getSelectedTheme() {
        if(themePreference != null) {
            return ThemeType.ThemeTypeFromInt(themePreference);
        }
        themePreference = sharedPreferences.getInt(THEME_PREF_KEY, 0);
        return ThemeType.ThemeTypeFromInt(themePreference);
    }
}
