#### 目录结构
- 1. 线程池简介
    - 1.1 是么是“池”
    - 1.2 为什么要使用线程池
    - 1.3 线程池的好处
- 2. 如何创建和停止线程池
    - 2.1线程池构造函数的参数
    - 2.2手动创建还是自动创建
    - 2.3线程数量如何设置
    - 2.4如何停止线程池
- 3. 线程池的拒绝策略
    - 3.1 拒绝时机
    - 3.2 4种拒绝策略
- 4. 钩子方法
    - 4.1 简介
    - 4.2 源码分析
- 5. 线程池实现原理
- 6. 使用线程池的注意点


### 1.线程池简介
#### 1.1 是么是“池”
“池”是一种“以空间换时间的做法”，我们在内存中保存一系列整装待命的对象，供调用放使用。能大量的节省系统频繁的创建销毁对象所用的时间。我们可以把“池”理解为一个工厂，工厂里面有一定数量的工人，在订单较少的情况下，工人可能会看起来占用了资源，但是订单多起来的时候，工人们就能快速的处理订单，等待工人慢慢处理，我们不用去考虑招聘，培训的成本。
使用“池”的作用
1）复用已有资源
2）控制资源总量

#### 1.2 为什么要使用线程池
1) 问题一：反复创建线程开销大
在Java中，如果每个请求到达就去创建一个新线程，开销是相当大的，因为每一个Java的线程就对应一个操作系统的线程。在实际使用中，线程的创建和销毁都是需要时间的，如果是一个非常轻量级的请求，服务器也要新创建一条线程去处理，那么有可能创建和销毁线程消耗的时间，比请求处理的时间还更长。

2) 问题二：过多的线程会占用太多内存
创建过多的线程还会导致内存溢出。活动的线程需要消耗系统资源，如果在一个JVM里创建太多的线程，可能会使系统由于过度消耗内存或“切换过度”而导致系统资源不足。

#### 1.3 线程池的好处
1.   线程池解决了线程生命周期开销问题和系统资源不足的问题
线程池刚创建的时候，会先创建一定数量的线程，比如说10个，这样当有新请求过来的时候，就可以直接从池子里取出一个已经创建好的线程，直接开始处理请求，这样就省去了创建线程的时间。
再加上对线程的重复使用，就大大减小了线程生命周期的开销，而且由于在请求到达时线程已经存在，所以消除了线程创建所带来的延迟，使用应用程序响应更快，增强了用户体验。
2.   合理统筹内存和CPU的使用
通过灵活适当地调整线程中的线程数目，使得既不会由于线程太多导致内存溢出（避免抛出java.lang.OutOfMemoryError: unable to create new native thread），也不会由于线程太少导致浪费CPU资源，达到了完美平衡。
3.   统一管理资源
使用线程池可以统一管理任务队列和线程，例如可以统一开始和结束，比单个线程逐一处理任务要更方便、更易于管理。同时这样也便于数据统计，因为每个ThreadPoolExecutor还维护一些基本统计数据，例如已完成任务的数量。

### 2.创建和停止线程池
#### 2.1线程池构造函数的参数
|  参数名   | 类型  |  作用 |
| ---- | ---- | ---- |
| corePoolSize  | int |核心线程数|
| maxPoolSize  | int |最大线程数|
| keepAliveTime  | long |保持存活时间|
| unit  | TimeUnit | 时间单位|
| workQueue  | BlockingQueue |任务存储队列|
| threadFactory  | ThreadFactory | 创建新的线程|
| hander  | RejectExecutionHandler单元 |线程池的拒绝策略|
corePoolSize：核心线程数，核心线程会长期保存在内存中不被销毁
maxPoolSize: 最大线程数，任务过多的时候，超过了核心线程数，和存储队列，会额外的新建一些线程，这些新增线程加上核心线程数的上限就是最大线程数。
keepAliveTime、unit：非核心线程没有任务处理时保持存活的时间，超时后会回收非核心线程
hander：拒绝策略，最大线程数和存储队列的人物都满了后，拒绝任务
threadFactory：线程工厂，通常情况下使用默认的线程工厂defaultThreadFactory()即可，如果自己指定ThreadFactoy就可以改变线程名、线程租、优先级、是否是守护线程等。
workQueue： 3种常见的队列
1) 直接交换，Synchornousueue 队列中不存放任务， maxPoolSize需要设置大一些
2) 无界队列： LinkedBlockingQueue 队列的容量无限大，处理的任务跟不上新建的任务，可能会造成OOM
3) 有界队列： ArrayBlockingQueue 

#### 2.2手动创建还是自动创建
手动创建更好，因为这样可以让我们更加明确线程池的运行规则，避免资源耗尽的风险。
自动创建（调用JDK封装好的构造函数）带来的问题
FixedThreadPool： corePoolSize等于maxPoolSize，线程数为核心线程数，队列是一个无界的LinkedBlockingQueue，可能会导致OOM。下面代码演示
````java
public class FixedThreadPoolOOM {
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new SubThread());
        }
    }
}

class SubThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
````
![](/img/FixThreadPoolOOm.png "FixThreadPoolOOm.png")

SingleThreadPool: 实现方式和FixedThreadPool类似，只不过corePoolSize和maxPoolSize都为1，同样也会带来OOM的问题。

CacheThreadPool：核心线程数为0，最大线程数为Integer.MAX_VALUE,阻塞队列为SynchronousQueue，每次新来任务都会去新建线程，同样也会带来OOM的问题。

cheduledThreadPool：核心线程数用户自己设置，为Integer.MAX_VALUE,阻塞队列为DelayedWorkQueue，可以处理一些延时的任务，同样也会有OOM的问题。
#### 2.3线程数量如何设置
如何设置线程数其实就是需要我们考虑如何在占用内存空间的情况下榨干CPU。我们可以通过我们的任务种类，采用下面两种方案
1）CPU密集型任务(加密、计算hash)：最佳线程数为CPU核心数+1，因为处理这些任务时，CPU已经满负荷了，新建再多的线程会增加上下文的切换次数，带来额外的开销。理论上线程的数量 = CPU 核数就是最合适的，不过通常把线程的数量设置为CPU 核数 +1，会实现最优的利用率。即使当密集型的线程由于偶尔的内存页失效或其他原因导致阻塞时，这个额外的线程也能确保 CPU 的时钟周期不会被浪费，从而保证 CPU 的利用率。
2）IO密集型(读写数据库、文件、网络访问)：IO密集型是指在处理任务时，IO过程所占用的时间较多，在这种情况下，线程数的计算方法可以分为两种：
　　方法一：线程数=cpu核心数*2，cpu所占用时间不多，可让cpu在等待IO的时候去处理其他任务，充分利用cpu。
　　方法二：线程等待时间比例越多，需要更多的线程，而线程cpu所占时间越多，则需要更少线程数。
　　线程数=（（线程等待时间+线程cpu时间）/线程cpu时间）*cpu核心数。
#### 2.4如何停止线程池
1) shutdown()： 执行该方法后只是通知线程池去开始停止，会将存量的任务都执行完毕(包括线程中正在执行的任务和工作队列中待执行的任务),在存量任务执行完线程池真正的关闭前再添加任务则会抛出异常;
2）isShutdown() 执行shutdown()后,该方法返回true;
3) isTerminated() 线程池完全停止后，该方法返回true;
4) awaitTermination() 等待一段时间后，返回线程池是否完全关闭;
5) shutdownNow() 正在执行的任务会收到中断信号，工作队列中的任务会作为该方法的返回值返回

### 3.拒绝策略
#### 3.1拒绝时机
1）当线程池关闭时，提交新任务会被拒绝，这个其实就是发生在我们上一节提到的shutdown()方法后;
2）当线程池已经达到最大线程数并且工作队列已经存满时。
#### 3.2 4种拒绝策略
1）AbortPolicy 直接抛出异常;
2）DiscardPolicy 丢弃新加入的任务，不会抛出异常;
3）DiscardOldestPolicy 丢弃队列中最老的任务;
4）CallerRunsPolicy 让提交任务的线程运行该任务。该策略不会造成损失，并且能形成负反馈，因为执行该线程执行任务也需要花费时间，而只有执行完任务后才能去提交新的任务

### 4.钩子函数beforeExecute()和afterExecute()
#### 4.1简介
钩子函数父类定义的一些空实现的方法，子类通过实现这些方法，在程序运行的声明周期中的某个阶段来回调这些方法，实现我们自定义的功能。例如日志记录，数据统计等。
java的线程池ThreadPoolExecutor为我们提供了三个钩子函数：
beforeExecute：在执行任务之前回调
afterExecute：在任务执行完后回调
terminated：在线程池中的所有任务执行完毕后回调
#### 4.2源码分析
````java
final void runWorker(Worker w) {
    ...
    try {
        beforeExecute(wt, task);
        Throwable thrown = null;
        try {
            task.run();
        } catch (RuntimeException x) {
            thrown = x; throw x;
        } catch (Error x) {
            thrown = x; throw x;
        } catch (Throwable x) {
            thrown = x; throw new Error(x);
        } finally {
            afterExecute(task, thrown);
        }
    } finally {
        task = null;
        w.completedTasks++;
        w.unlock();
    }    
    ...
}
````
可以看到再执行Runnable的run()前后分别调用了beforeExecute()和afterExecute()方法，我们可以重写这些钩子函数，在任务执行前后来处理我们的业务。

### 5.线程池实现原理
#### 5.1线程池组成部分
1）线程池管理器
2) 工作线程
3）任务队列
4）任务接口(Task)
下图描述线程池的结构
![](/img/线程池结构.png "线程池结构.png")
#### 5.2线程池代码实现
线程池类关系图:
![](/img/线程池类关系图.png "线程池类关系图.png")
#### 5.3线程池实现任务复用