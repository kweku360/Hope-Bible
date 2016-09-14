package com.kfive.hopebible;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by kweku on 9/9/2016.
 */
public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
