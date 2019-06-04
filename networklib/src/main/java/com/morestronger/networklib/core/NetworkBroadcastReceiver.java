package com.morestronger.networklib.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.morestronger.networklib.util.NetstatusUtil;

/**
 * project NetworkBus
 * author 王越强
 * describe 即时网络广播.
 * date 2019/5/30
 * time 23:10
 */
public class NetworkBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent || null == intent.getAction()) {
            return;
        }
        if (intent.getAction().equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) {
            // 获取当前网络状态类型.
            NetworkBusHelper.getDefault().postSubscribe(NetstatusUtil.getCurrentNetType());
        }
    }

}
