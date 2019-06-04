package com.morestronger.networkbus;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.morestronger.networklib.NetworkBus;
import com.morestronger.networklib.annotation.NetworkSubscribe;
import com.morestronger.networklib.type.NetFilterType;
import com.morestronger.networklib.type.NetType;

public class MainActivity extends AppCompatActivity {
    private TextView tvNetType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvNetType = findViewById(R.id.tv_net_type);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkBus.getDefault().register(MainActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetworkBus.getDefault().unregister(MainActivity.this);
    }

    @NetworkSubscribe(netFilterType = NetFilterType.ALL)
    public void netWorkChangeCallBack(NetType netType) {
        switch (netType) {
            case MOBILE:
                Log.e("MainActivity", "MainActivity 已连接移动网络");
                tvNetType.setText("MainActivity 已连接移动网络");
                break;
            case NONE:
                Log.e("MainActivity", "MainActivity 网络断开");
                tvNetType.setText("MainActivity 网络断开");
                break;
            case WIFI:
                Log.e("MainActivity", "MainActivity 已连接到wifi");
                tvNetType.setText("MainActivity 已连接到wifi");
                break;
            default:
                break;
        }
    }
}
