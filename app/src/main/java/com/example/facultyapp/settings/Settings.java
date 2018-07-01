package com.example.facultyapp.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

public class Settings {
    //Keys for Shared preferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "faculty";
    //Check if the User is logged in or not
    public static final String LOGGED_IN_SHARED_PREF = "loggedin";

    private static SharedPreferences settings;

    public static void init(@NonNull Context context) {
        settings = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set Logged in
     */
    public static boolean isLoggedIn() {
        return settings.getBoolean(LOGGED_IN_SHARED_PREF, false);
    }

    public static void setLoggedInSharedPref(boolean loggedIn) {
        settings.edit()
                .putBoolean(LOGGED_IN_SHARED_PREF, loggedIn)
                .apply();
    }
}
