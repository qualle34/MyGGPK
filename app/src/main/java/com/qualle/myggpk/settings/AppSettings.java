package com.qualle.myggpk.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppSettings {

    public static int getGroupId(Context applicationContext){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        return Integer.parseInt(sharedPreferences.getString("pref_group_list", "1"));
    }

    public static boolean getNightModeSettings(Context applicationContext) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        return sharedPreferences.getString("pref_night_mode_list", "false").equals("true");
    }

    public static int getLanguageSettings(Context applicationContext) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        return Integer.parseInt(sharedPreferences.getString("pref_language_list", "1"));
    }

    public static boolean isVip(Context applicationContext){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        return sharedPreferences.getBoolean("password", false);
    }

    public static void setVip(Context applicationContext, boolean value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        sharedPreferences.edit().putBoolean("password", value).apply();
    }
}
