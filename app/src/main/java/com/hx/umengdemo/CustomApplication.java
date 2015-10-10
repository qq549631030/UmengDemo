package com.hx.umengdemo;

import android.app.Application;

import com.umeng.update.UmengUpdateAgent;

/**
 * Created by huangxiang on 15/10/9 23:15.
 */
public class CustomApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        UmengUpdateAgent.setDeltaUpdate(true);
    }
}
