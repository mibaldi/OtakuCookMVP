package com.mibaldipabjimcas.otakucookmvp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.RelativeLayout;


import com.mibaldipabjimcas.otakucookmvp.R;

/**
 * Created by mikelbalducieldiaz on 30/7/16.
 */

public class ThemeUtils {

    public static void applyThemeIntoToolbar(Context context, ThemeType theme, Toolbar toolbar) {
        int color = ColorUtils.ColorFromTheme(context, theme);
        toolbar.setBackgroundColor(color);
    }

    public static void applyThemeIntoStatusBar(Activity activity, ThemeType theme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = ColorUtils.makeColorDarker(ColorUtils.ColorFromTheme(activity, theme));
            activity.getWindow().setStatusBarColor(color);
        }
    }
    public static void applyThemeIntoNavigationView(Context context, ThemeType theme, NavigationView navigationView){
        if (theme ==ThemeType.BLUE){
            navigationView.setItemBackground(ContextCompat.getDrawable(context, R.drawable.drawer_item));
        }else{
            navigationView.setItemBackground(ContextCompat.getDrawable(context,R.drawable.drawer_item_black));
        }

    }
    public static void applyThemeIntoFloatingActionButton(Context context, ThemeType theme, android.support.design.widget.FloatingActionButton floatingActionButton){
        int color = ColorUtils.ColorFromTheme(context, theme);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(color));
    }
    public static void applyThemeIntoButton(Context context,ThemeType theme,Button button){
        int color = ColorUtils.ColorFromTheme(context, theme);
        button.setBackgroundColor(color);

    }
    public static void applyThemeIntoButtonReset(Context context,ThemeType theme,Button button){
        int color = ColorUtils.ColorFromThemeReset(context, theme);
        button.setBackgroundColor(color);

    }
    public static void applyThemeIntoCollapsing(Context context, ThemeType theme, CollapsingToolbarLayout collapsingToolbarLayout){
        int color = ColorUtils.ColorFromTheme(context, theme);
        collapsingToolbarLayout.setContentScrimColor(color);
    }
    public static void applyThemeIntoRelative(Context context, ThemeType theme, RelativeLayout relativeLayout){
        int color = ColorUtils.ColorFromThemeReset(context, theme);
        relativeLayout.setBackgroundColor(color);
    }

}