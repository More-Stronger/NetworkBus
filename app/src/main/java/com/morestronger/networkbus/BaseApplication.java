package com.morestronger.networkbus;

import android.app.Application;

import com.morestronger.networklib.NetworkBus;

/**
 * project NetworkBus
 * author 王越强
 * describe
 * date 2019/6/3
 * time 16:22
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkBus.getDefault().init(this);
    }
}
