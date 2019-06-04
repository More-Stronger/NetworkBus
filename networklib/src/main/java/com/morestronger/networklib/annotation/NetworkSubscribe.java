package com.morestronger.networklib.annotation;

import com.morestronger.networklib.type.NetFilterType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * project NetworkBus
 * author 王越强
 * describe 网络监听注解.
 * date 2019/5/31
 * time 0:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NetworkSubscribe {
    // 过滤类型.默认为不过滤,有改变则都会执行方法.
    NetFilterType netFilterType() default NetFilterType.ALL;
}
