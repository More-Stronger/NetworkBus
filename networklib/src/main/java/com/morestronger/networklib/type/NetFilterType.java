package com.morestronger.networklib.type;

/**
 * project NetworkBus
 * author 王越强
 * describe 网络过滤类型.
 * date 2019/5/31
 * time 0:05
 */
public enum NetFilterType {
    // 默认为不过滤,① WIFI连接或断开执行方法 ②在WIFI断开情况下,移动网络连接或断开执行方法.
    ALL,

    // WIFI过滤,WIFI连接和断开时会执行方法.
    WIFI,

    // 移动网络过滤,在WIFI断开情况下,移动网络连接和断开时会执行方法.
    MOBILE,

    // 网络断开过滤,① WIFI断开执行方法  ②在WIFI断开情况下,移动网络断开时会执行方法.
    NONE
}
