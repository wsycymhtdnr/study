### Android系统启动
![Android系统架构图](img\Android架构.png "Android系统架构")
1. 按电源键通电后，引导芯片启动，引导芯片开始从固化的ROM里的预设代码执行，加载引导程序到RAM，bootloader检查RAM，初始化硬件参数
2. Kernel层主要加载一些硬件设备驱动，初始化进程管理、内存管理、加载Driver驱动文件等，启动内存核心进程。（相机驱动，输入驱动等，解决硬件提供商标准不同意的原因）
3. 硬件设备驱动与HAL层进行交互，启动init进程，它启动后会启动adbd，logd等用户守护进程，同时fork出zygote进程。zygote进程是承上启下的存在。同时会反射调用com.android.internal.os.ZygoteInit进入Java层。（abd打印日志，断点调试。zygote进程java层面入口）
4. zygote进程启动完毕他会加载虚拟机，启动System Server进程（zygote孵化的第一个进程）;
System Server负责启动和管理整个Android FrameWork，包含ActivityManager，WindowManager，PackageManager，PowerManager等服务。
5. zygote同时会启动相关的APP进程，他启动的第一个APP进程为Launcher，所有的APP进程都有zygote fork生成。

* C++调用ZygoteInit.java进入到Java世界
![C++调用ZygoteInit](img\C++调用ZygoteInit.png "C++调用ZygoteInit")

``` java
ZygoteInit.java
public static void main(String argv[]){
  //1.预加载frameworks/base/preloaded-classes和framework_res.apk资源，linux在fork进程时，只是为子进程创建一个数据结构，使子进程地址空间映射到与父进程相同的物理内存空间。虚拟机的创建也是实例拷贝,共享系统资源，如果每创建一个虚拟机都加载一份系统资源，将会非常耗时和浪费。子进程在刚fork生成时，完全共享父进程的进程物理空间，采用写时复制的方式共享资源。(提高APP启动的响应速度)
    preloadClasses();
    preloadResources();
    preloadSharedLibraries();
       
   // 2. 启动system_server进程。该进程是framework的核心。
    if(argv[1].equals("start-system-server")){
            startSystemServer(); 
     } 
  
   //3.创建Socket服务，用于接收AMS创建进程的请求
    registerZygoteSocket();
  
   //4.进入阻塞状态，等待连接，用以处理来自AMS申请进程创建的请求
  runSelectLoopMode(); 
    } 
}
```
* SystemServer进程中启动的核心系统服务
![SystemServer核心系统服务](img\SystemServer核心系统服务.png "SystemServer核心系统服务")

* SystemServer的main方法主要做了以下三件事情，即启动三种不同类型的系统服务。
``` java
SystemServer.java
public static void main(String argv[]){
  //创建系统的服务的管理者
  SystemServiceManager mSystemServiceManager = new SystemServiceManager(mSystemContext);
   //启动引导服务
   startBootstrapServices();
   //启动核心
   startCoreServices();
  //启动其他一般服务
   startOtherServices();
}
```

* 在startOtherServices()方法的最后，会远程调用到AMS中的SystemReady()方法。该方法是Launcher应用的入口
``` java
ActivityManagerService.java
public void systemReady(...){
 //开始拉起launcher应用的
 //ActivityTaskManagerInternal是抽象类,它的实现类是ATMS的内部类LocalService
 mAtmInternal.startHomeOnAllDisplays(currentUserId, "systemReady");
}  
```

### Launcher启动流程
* Launcher进程启动流程
![Launcher启动流程图](img\Launcher启动流程图.png "Launcher启动流程图")

* ActivityManangerService Activity生命周期管理的调度，SystemReady()Launcher应用的入口
* ActivityTaskManagerService 在Android10之前对Activity的管理工作转移到该类里。
* RootActivityContainer 调用PMS查询系统中已安装的应用哪个符合Launcher标准并得到一个Intent对象，然后交由ActivityStarter启动
* ActivityStarter 对传入的Intent对象进行各种检查，如检查是否在清单文件注册，应用是否有权限启动等
* ActivityRecord service端对Activity实例的映射，包含Activity的所有信息
* TaskRecord 任务栈，包含一系列的Activity实例
* ActivityStack 任务栈的管理类
* ActivityStackSupervisor 每一个APP包含一个ActivityStack实例，ActivityStackSupervisor负责管理所有的ActivityStack实例。
* processList Android 10中新引入，将原来在AMS中启动进程的工作转移到该类中。
* ZygoteProcess 建立与Zygote进程通信的Socket连接，并且将传送创建进程所需要的参数。

``` java
ActivityTaskManagerInternal.java
    /** Start home activities on all displays that support system decorations. */
    public abstract boolean startHomeOnAllDisplays(int userId, String reason);
```
ActivityTaskManagerInternal对应的实现类就是在ActivityTaskManagerService的LocalService中
``` java
ActivityTaskManagerService#LocalService
 public boolean startHomeOnAllDisplays(int userId, String reason) {
            synchronized (mGlobalLock) {
                return mRootActivityContainer.startHomeOnAllDisplays(userId, reason);
            }
        }
```
在startHomeOnAllDisplays()方法中又把启动桌面应用的工作委托给了RootActivityContainer

``` java
RootActivityContainer.java
// 遍历所有的屏幕，依次启动Launcher应用
boolean startHomeOnAllDisplays(int userId, String reason) {
        boolean homeStarted = false;
        for (int i = mActivityDisplays.size() - 1; i >= 0; i--) {
            final int displayId = mActivityDisplays.get(i).mDisplayId;
            homeStarted |= startHomeOnDisplay(userId, reason, displayId);
        }
        return homeStarted;
    }
```
继续跟进startHomeOnDisplay()
``` java
RootActivityContainer.java
boolean startHomeOnDisplay(int userId, String reason, int displayId, boolean allowInstrumenting, boolean fromHomeKey) {
    ...
    if (displayId == DEFAULT_DISPLAY) {
            homeIntent = mService.getHomeIntent();
            aInfo = resolveHomeActivity(userId, homeIntent);
        }
    ...
}
```
在if里面其实就是构建出Launcher的Intent对象

``` java
ActivityTaskManagerService.java
Intent getHomeIntent() {
        Intent intent = new Intent(mTopAction, mTopData != null ? Uri.parse(mTopData) : null);
        intent.setComponent(mTopComponent);
        intent.addFlags(Intent.FLAG_DEBUG_TRIAGED_MISSING);
        if (mFactoryTest != FactoryTest.FACTORY_TEST_LOW_LEVEL) {
            intent.addCategory(Intent.CATEGORY_HOME);
        }
        return intent;
    }
```
可以看到Category和我们平时声明的Intent.CATEGORY_LAUNCHER有所不同，将Activity声明为Intent.CATEGORY_HOME,他讲变成一个Launcher应用。
同样在startHomeOnDisplay()方法中，最后会调用PMS查询系统中已安装的应用哪个符合Launcher标准
调用到ActivityStartController#startHomeActivity()

