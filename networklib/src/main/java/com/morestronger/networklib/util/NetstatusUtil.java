package com.morestronger.networklib.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.morestronger.networklib.NetworkBus;
import com.morestronger.networklib.type.NetType;

/**
 * project NetworkBus
 * author 王越强
 * describe 网络状态工具类.
 * date 2019/5/31
 * time 1:29
 */
public class NetstatusUtil {

    /**
     * 获取当前网络状态.
     */
    public static NetType getCurrentNetType() {
        ConnectivityManager manager = (ConnectivityManager) NetworkBus
                .getDefault()
                .getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == manager) {
            return NetType.NONE;
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (null == networkInfo) {
            return NetType.NONE;
        }
        int networkType = networkInfo.getType();
        if (networkType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        } else if (networkType == ConnectivityManager.TYPE_MOBILE) {
            return NetType.MOBILE;
        } else {
            return NetType.NONE;
        }
    }

    /**
     * 是否有网络连接.
     *
     * @return true为有网络连接, false为无网络连接.
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) NetworkBus
                .getDefault()
                .getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] infos = manager.getAllNetworkInfo();
        if (infos != null) {
            for (NetworkInfo info : infos) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
}
