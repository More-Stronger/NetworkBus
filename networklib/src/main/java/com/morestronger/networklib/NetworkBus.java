package com.morestronger.networklib;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.morestronger.networklib.core.NetworkBroadcastReceiver;
import com.morestronger.networklib.core.NetworkBusHelper;
import com.morestronger.networklib.core.NetworkCallbackImpl;

/**
 * project NetworkBus
 * author 王越强
 * describe 网络监听总线,提供外部调用.
 * date 2019/5/30
 * time 23:10
 */
public class NetworkBus {
    private static volatile NetworkBus instance;
    private Application application;
    private NetworkBroadcastReceiver networkBroadcastReceiver;
    private NetworkCallbackImpl networkCallback;

    private NetworkBus() {
    }

    public static NetworkBus getDefault() {
        if (null == instance) {
            synchronized (NetworkBus.class) {
                if (null == instance) {
                    instance = new NetworkBus();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化时间总线,需要在合理的地方进行初始化,比如application的onCreate中.
     */
    public void init(Application application) {
        this.application = application;
        NetworkBusHelper.getDefault().init();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0及以上通过 ConnectivityManager.NetworkCallback 实现网络实时监听.
            networkCallback = new NetworkCallbackImpl();
            NetworkRequest request = new NetworkRequest.Builder().build();
            ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                cm.registerNetworkCallback(request, networkCallback);
            }
        } else {
            // 5.0之前通过 广播 实现网络即时监听.
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            networkBroadcastReceiver = new NetworkBroadcastReceiver();
            application.registerReceiver(networkBroadcastReceiver, intentFilter);
        }
    }

    /**
     * 释放资源 因为5.0之前采用动态广播注册,需要在合理的地方进行释放.
     */
    public void release() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0及以上通过 ConnectivityManager.NetworkCallback 实现网络实时监听.
            if (null != networkCallback) {
                ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
                cm.unregisterNetworkCallback(networkCallback);
            }
        } else {
            // 5.0之前通过 广播 实现网络即时监听.
            if (null != networkBroadcastReceiver) {
                application.unregisterReceiver(networkBroadcastReceiver);
            }
        }
        NetworkBusHelper.getDefault().release();
    }

    public Application getApplication() {
        return application;
    }

    /**
     * 注册订阅.
     *
     * @param object 注册对象,比如Activity,Fragment.
     */
    public void register(Object object) {
        NetworkBusHelper.getDefault().register(object);
    }

    /**
     * 注销订阅.
     *
     * @param object 注销对象,比如Activity,Fragment.
     */
    public void unregister(Object object) {
        NetworkBusHelper.getDefault().unregister(object);
    }

    /**
     * 注销所有订阅.
     */
    public void unregisterAll() {
        NetworkBusHelper.getDefault().unregisterAll();
    }
}
