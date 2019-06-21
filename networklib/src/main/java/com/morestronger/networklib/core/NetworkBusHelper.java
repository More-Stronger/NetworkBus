package com.morestronger.networklib.core;

import android.util.ArrayMap;
import android.util.Log;

import com.morestronger.networklib.annotation.NetworkSubscribe;
import com.morestronger.networklib.bean.NetSubscribeMethodBean;
import com.morestronger.networklib.type.NetType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * project NetworkBus
 * author 王越强
 * describe 即时网络辅助类.
 * date 2019/6/3
 * time 14:11
 */
public class NetworkBusHelper {
    private static volatile NetworkBusHelper instance;

    /**
     * 当前网络类型.
     */
    private NetType curNetType;

    /**
     * 更新网络后需要通知的集合.
     * 例:key: MainActivity   value: Test1方法 Test2方法.
     */
    private Map<Object, List<NetSubscribeMethodBean>> networkMap;

    private NetworkBusHelper() {
    }

    public static NetworkBusHelper getDefault() {
        if (null == instance) {
            synchronized (NetworkBusHelper.class) {
                if (null == instance) {
                    instance = new NetworkBusHelper();
                }
            }
        }
        return instance;
    }

    ////////////////////////////////////////////////////////////
    ////////////////////  私有方法.
    ////////////////////////////////////////////////////////////

    /**
     * 查找对象中合格的网络监听注解方法集合.
     *
     * @param object 要查找的对象,比如activity,fragment.
     * @return 合格网络监听注解方法集合.
     */
    private List<NetSubscribeMethodBean> findNetworkAnnotation(Object object) {
        List<NetSubscribeMethodBean> methodList = new ArrayList<>();
        // 获取当前类及父类的所有public方法.
        Method[] methods = object.getClass().getMethods();
        // 循环遍历所有public方法,找到使用了网络注解的方法.
        for (Method method : methods) {
            // 校验方法第一步,检查方法是否是NetworkSubscribe注解.
            NetworkSubscribe networkAnnotation = method.getAnnotation(NetworkSubscribe.class);
            if (null == networkAnnotation) {
                continue;
            }
            // 校验方法第二步,返回参数类型只有一个.
            Class<?>[] methodParameterTypes = method.getParameterTypes();
            if (methodParameterTypes.length != 1) {
                continue;
            }
            // 校验方法第三步,返回参数类型为NetType.
            Class<?> methodParameterType = methodParameterTypes[0];
            if (!methodParameterType.isAssignableFrom(NetType.class)) {
                continue;
            }
            Log.i("NetworkBus", object.getClass().getName() + "===" + method.getName());
            methodList.add(new NetSubscribeMethodBean(networkAnnotation.netFilterType(), methodParameterType, method));
        }
        return methodList;
    }

    /**
     * 执行订阅了网络监听的方法.
     *
     * @param netSubscribeMethodBean 订阅网络监听方法实体.
     * @param object                 订阅对象.
     * @param curNetType             当前网络类型.
     */
    private void netSubscribeMethodInvoke(NetSubscribeMethodBean netSubscribeMethodBean, Object object, NetType curNetType) {
        try {
            netSubscribeMethodBean.getMethod().invoke(object, curNetType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络改变,对订阅对象执行方法.
     */
    protected void postSubscribe(NetType netType) {
        this.curNetType = netType;
        // 循环遍历需要通知的对象集合.
        Set<Object> objects = networkMap.keySet();
        for (Object object : objects) {
            // 当前对象中,订阅了网络监听的方法集合.
            List<NetSubscribeMethodBean> netSubscribeMethodBeans = networkMap.get(object);
            if (null == netSubscribeMethodBeans) {
                return;
            }
            // 循环遍历当前对象下,网络监听的方法集合,执行方法.
            for (NetSubscribeMethodBean netSubscribeMethodBean : netSubscribeMethodBeans) {
                switch (netSubscribeMethodBean.getNetFilterType()) {
                    case ALL:
                        netSubscribeMethodInvoke(netSubscribeMethodBean, object, curNetType);
                        break;
                    case WIFI:
                        if (curNetType == NetType.WIFI || curNetType == NetType.NONE) {
                            netSubscribeMethodInvoke(netSubscribeMethodBean, object, curNetType);
                        }
                        break;
                    case MOBILE:
                        if (curNetType == NetType.MOBILE || curNetType == NetType.NONE) {
                            netSubscribeMethodInvoke(netSubscribeMethodBean, object, curNetType);
                        }
                        break;
                    case NONE:
                        if (curNetType == NetType.NONE) {
                            netSubscribeMethodInvoke(netSubscribeMethodBean, object, curNetType);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////
    ////////////////////  公共方法.
    ////////////////////////////////////////////////////////////

    /**
     * 初始化数据.
     */
    public void init() {
        curNetType = NetType.NONE;
        networkMap = new ArrayMap<>();
    }

    /**
     * 清空资源.
     */
    public void release() {
        if (networkMap != null) {
            networkMap.clear();
            networkMap = null;
        }
    }

    /**
     * 注册订阅.
     *
     * @param object 注册对象,比如Activity,Fragment.
     */
    public void register(Object object) {
        // 如果已经注册过,则直接返回.
        if (!networkMap.isEmpty() && null != networkMap.get(object)) {
            return;
        }
        List<NetSubscribeMethodBean> networkMethodList = findNetworkAnnotation(object);
        networkMap.put(object, networkMethodList);
    }

    /**
     * 注销订阅
     *
     * @param object 注销对象,比如Activity,Fragment.
     */
    public void unregister(Object object) {
        if (!networkMap.isEmpty()) {
            networkMap.remove(object);
        }
    }

    /**
     * 注销所有订阅.
     */
    public void unregisterAll() {
        if (!networkMap.isEmpty()) {
            networkMap.clear();
        }
    }
}
