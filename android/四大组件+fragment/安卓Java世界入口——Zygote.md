#### 目录介绍
- 1.Zygote作用
    - 1.1 启动SystemServer 常用类 jni函数 主题资源 共享库
    - 1.2 孵化应用进程
- 2.Zygote进程的启动流程
    - 2.1 如何启动 init进程linux用户空间第一个进程 
     1、读取init.rc配置文件(定义了要启动的系统服务，serviceManager) fork+execve启动服务
     标红路径 标蓝 参数
     启动进程的方式
     1、fork + handler  2、fork + execve
     信号处理-sigchld
     子进程挂了父进程会受到sigchld信号重启子进程

     Zygote启动后做了什么事情
     Native 世界 execve系统调用
     1、启动虚拟机
     2、注册Android的jni函数
     3、进入Java世界

     java世界
     1、预加载资源
     2、启动systemService
     3、进入Loop循环，等待socket请求
     处理请求
     1、读取参数列表
     2、根据参数启动子进程
     3、handleChildProc() ActivityThread.main()

     注意细节
     Zygote fork要保证单线程 因为子进程创建只有一个主线程，否则会有死锁等问题，zygote进程先停掉其他线程，fork完成后再重启
     跨进程通信没用使用binder

     为什么用zygote孵化应用进程不用systemserver 


    - 2.2 ZygoteInit类的main方法
    - 2.3 registerZygoteSocket(socketName)分析
    - 2.4 preLoad()方法分析
    - 2.5 startSystemServer()启动进程