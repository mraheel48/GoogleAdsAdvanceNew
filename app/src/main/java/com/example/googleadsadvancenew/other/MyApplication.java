package com.example.googleadsadvancenew.other;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.multidex.MultiDexApplication;

import com.example.googleadsadvancenew.adUtils.AppOpenManager;

@SuppressLint("StaticFieldLeak")
public class MyApplication extends MultiDexApplication {

    public static Context context;

    public static AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        /*//Ads init
        MobileAds.initialize(this);

        appOpenManager = new AppOpenManager(this);*/
    }
}
