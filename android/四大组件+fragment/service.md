长时间运行操作而不提供界面的组件
分类
前台服务 音乐播放 必须显示通知
后台服务 WorkManager 可替代
绑定服务 可用来实现c-s结构，

服务在主线程中，耗时操作需要新建线程，否则会导致anr

通信 intent bindservice 回传IBinder (Binder Messager AIDL)

前台服务，需要在启动service几秒后调用startForeground() 原生Android前台服务不手动关掉是不会随应用退出的

startService 和 bindService() 生命周期 一起调用

遗留问题:不手动停止service，系统如何杀死service

快捷键ctrl+p 查看参数
个人理解：apply一般用于初始化对象，also用于对象生成后同时做某事
