# NetworkBus

[![](https://jitpack.io/v/More-Stronger/NetworkBus.svg)](https://jitpack.io/#More-Stronger/NetworkBus)

## 介绍

 * 一款即时网络监听框架,简化监听网络,使用方法和EventBus类似.
 * 支持AndroidX.
 * 支持4.4及以上API.
 * 5.0及以上通过 ConnectivityManager.NetworkCallback 实现网络实时监听,5.0之前通过广播实现监听.

## 添加依赖

#### 1.Add the JitPack repository to your build file

	allprojects {
         repositories {
			   ...
			   maven { url 'https://jitpack.io' }
		 }
	}

#### 2. Add the dependency

	dependencies {	  
	     implementation 'com.github.More-Stronger:NetworkBus:v1.0.1'
	}

## 使用

#### 1.在合理的地方进行初始化,尽可能早的进行初始化,比如 Application 的 onCreate 中.
	NetworkBus.getDefault().init(this);

#### 2.在需要监听的对象中,合理的地方进行注册,比如 Activity 的 onResume中.
	NetworkBus.getDefault().register(this);

#### 3.在需要监听的对象中,合理的地方进行注销,比如 Activity 的 onPause中.
	NetworkBus.getDefault().unregister(this);

#### 4.通过 NetworkSubscribe 注解添加即时网络监听.
    @NetworkSubscribe(netFilterType = NetFilterType.ALL)
    public void netWorkChangeCallBack(NetType netType) {
       // 方法体.
    }

## 属性说明

#### 1.注解 @NetworkSubscribe(netFilterType = NetFilterType.xxx) 中支持如下网络过滤类型.

 * NetFilterType.ALL
   <br>默认过滤类型,① WIFI 连接或断开执行方法 ②在 WIFI 断开情况下,移动网络连接或断开执行方法.

 * NetFilterType.WIFI
   <br>WIFI 过滤, WIFI 连接和断开时会执行方法.

 * NetFilterType.MOBILE
   <br>移动网络过滤,在WIFI断开情况下,移动网络连接和断开时会执行方法.

 * NetFilterType.NONE
   <br>网络断开过滤,① WIFI 断开执行方法  ②在 WIFI 断开情况下,移动网络断开时会执行方法.

#### 2.网络类型 NetType .

 * NetType.WIFI : WIFI网络.

 * NetType.MOBILE : 移动网络.

 * NetType.NONE : 没有网络.


## 注意

   1.需要在合理的地方进行释放资源,避免内存泄漏.如果是在 Application 中进行 init ,可以不调用该方法.

	NetworkBus.getDefault().release();

   2.取消所有对象监听网络.

    NetworkBus.getDefault().unregisterAll();
    
## 混淆
    
   因为监听框架内部使用到反射,所以需要再混淆文件中添加如下忽略内容:
    
    -keepclasseswithmembers class * {
        @com.morestronger.networklib.annotation.NetworkSubscribe <methods>;
    }
