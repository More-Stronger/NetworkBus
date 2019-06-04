package com.morestronger.networkbus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.morestronger.networklib.NetworkBus;
import com.morestronger.networklib.annotation.NetworkSubscribe;
import com.morestronger.networklib.type.NetFilterType;
import com.morestronger.networklib.type.NetType;

public class MainFragment extends Fragment {
    private TextView tvNetType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tvNetType = view.findViewById(R.id.tv_net_type);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        NetworkBus.getDefault().register(MainFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        NetworkBus.getDefault().unregister(MainFragment.this);
    }


    @NetworkSubscribe(netFilterType = NetFilterType.ALL)
    public void netWorkChangeCallBack(NetType netType) {
        switch (netType) {
            case MOBILE:
                Log.e("MainFragment", "MainFragment 已连接移动网络");
                tvNetType.setText("MainFragment 已连接移动网络");
                break;
            case NONE:
                Log.e("MainFragment", "MainFragment 网络断开");
                tvNetType.setText("MainFragment 网络断开");
                break;
            case WIFI:
                Log.e("MainFragment", "MainFragment 已连接到wifi");
                tvNetType.setText("MainFragment 已连接到wifi");
                break;
            default:
                break;
        }
    }
}
