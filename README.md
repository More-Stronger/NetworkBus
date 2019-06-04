# NetworkBus

[![](https://jitpack.io/v/More-Stronger/NetworkBus.svg)](https://jitpack.io/#More-Stronger/NetworkBus)

### 添加依赖：

#### 1.Add the JitPack repository to your build file
	allprojects {
         repositories {
			   ...
			   maven { url 'https://jitpack.io' }
		   }
	   }
      
#### 2. Add the dependency
	dependencies {
	          implementation 'com.github.More-Stronger:NetworkBus:1.0.0'
	   }
	   
### 使用：
	   
#### 1.在合理的地方进行初始化,尽可能早的进行初始化,比如application的onCreate中.
	NetworkBus.getDefault().init(this);
	
#### 2.在需要监听的对象上注册.
	NetworkBus.getDefault().register(this);
	
#### 3.通过 NetworkSubscribe 注解添加即时网络监听.
	@NetworkSubscribe(netFilterType = NetFilterType.ALL)
    	public void netWorkChangeCallBack(NetType netType) {
        
    	}
