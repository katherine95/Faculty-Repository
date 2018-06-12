package com.example.facultyapp;

import android.app.Application;
import android.graphics.Typeface;

import com.example.facultyapp.settings.Settings;
import com.example.facultyapp.util.TypeFactory;

import timber.log.Timber;

public class App extends Application {
    private static App mInstance;
    private TypeFactory mFontFactory;

    public static synchronized App getApp() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Plant a Debug Timber Tree to log :)
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // init Settings
        Settings.init(this);
        mInstance = this;
    }

    public Typeface getTypeFace(int type) {
        if (mFontFactory == null)
            mFontFactory = new TypeFactory(this);

        switch (type) {
            case Constants.REGULAR:
                return mFontFactory.getRegular();

            case Constants.BOLD:
                return mFontFactory.getBold();

            case Constants.LIGHT:
                return mFontFactory.getLight();

            default:
                return mFontFactory.getRegular();
        }
    }

    public interface Constants {
        int REGULAR = 1,
                BOLD = 2,
                LIGHT = 3;
    }
}
