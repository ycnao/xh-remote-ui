package com.xhuman.remote.ui;

import android.app.Application;
import android.content.Context;


/**
 * MyApplication
 * author: Created by 闹闹 on 2019/7/10
 * version: 1.0.0
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    public static Context applicationContext;

    public static final String TAG = "MyApplication";

    public static MyApplication instance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationContext = this;
    }


}
