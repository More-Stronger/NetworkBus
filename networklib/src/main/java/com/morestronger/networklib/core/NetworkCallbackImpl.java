package com.morestronger.networklib.core;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import com.morestronger.networklib.type.NetType;
import com.morestronger.networklib.util.NetstatusUtil;

/**
 * project NetworkBus
 * author 王越强
 * describe 5.0及以上网络即时监听.
 * date 2019/6/3
 * time 11:47
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        if (NetstatusUtil.isNetworkAvailable()) {
            return;
        }
        postSubscribeInMainLooper(NetType.NONE);
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                postSubscribeInMainLooper(NetType.WIFI);
            } else {
                postSubscribeInMainLooper(NetType.MOBILE);
            }
        }
    }

    /**
     * 切换到主线程通知外部.
     */
    private void postSubscribeInMainLooper(final NetType netType) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                NetworkBusHelper.getDefault().postSubscribe(netType);
            }
        });
    }
}
