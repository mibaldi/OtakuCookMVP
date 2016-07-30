package com.mibaldipabjimcas.otakucookmvp.Utils;

/**
 * Created by mikelbalducieldiaz on 30/7/16.
 */

public enum ThemeType {
    ORANGE, BLUE;

    public static ThemeType ThemeTypeFromInt(int type){
        if(type == 1) {
            return ThemeType.BLUE;
        } else {
            return ThemeType.ORANGE;
        }
    }
}
