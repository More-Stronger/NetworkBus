package com.morestronger.networklib.bean;

import com.morestronger.networklib.type.NetFilterType;

import java.lang.reflect.Method;

/**
 * project NetworkBus
 * author 王越强
 * describe 使用了网络即时监听注解的实体.
 * 例:
 * *** @NetworkSubscribe(netFilterType = NetFilterType.ALL)
 * *** public void test(NetType netType) {
 * *** }
 * date 2019/5/31
 * time 0:38
 */
public class NetSubscribeMethodBean {
    /**
     * 网络过滤 对应(netFilterType = NetFilterType.ALL).
     */
    private NetFilterType netFilterType;

    /**
     * 回调参数类型,对应(NetType netType).
     */
    private Class<?> parameterType;

    /**
     * 注解的对应方法,对应test.
     */
    private Method method;

    public NetSubscribeMethodBean(NetFilterType netFilterType, Class<?> parameterType, Method method) {
        this.netFilterType = netFilterType;
        this.parameterType = parameterType;
        this.method = method;
    }

    public NetFilterType getNetFilterType() {
        return netFilterType;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public Method getMethod() {
        return method;
    }
}
