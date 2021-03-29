package com.example.aftersale;

import android.app.Application;

import com.example.aftersale.Model.Prefrancemanger;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Prefrancemanger.initialize(this);
    }
}
