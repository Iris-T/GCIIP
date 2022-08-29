package cn.iris.gciip.util;

import cn.iris.gciip.concurrent.MyThreadFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.*;

/**
 * @author Iris
 * @ClassName ThreadPoolUtil
 * @Description 自定义线程池工具类
 * @date 2022/8/29 13:45
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadPoolUtil {

    // 核心线程数
    @Value("${concurrent.core-pool-size}")
    private static int CORE_POOL_SIZE;

    // 线程池最大线程数
    @Value("${concurrent.max-pool-size}")
    private static int MAX_POOL_SIZE;

    // 有界阻塞阻塞队列大小
    @Value("${concurrent.queue-size}")
    private static int QUEUE_SIZE;

    // 超出核心线程数的线程存活时间，单位: 秒
    @Value("${concurrent.keep-alive-time}")
    private static int KEEP_ALIVE_TIME;

    // 工作线程队列
    private static final BlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(QUEUE_SIZE);

    // 线程工厂
    private static final ThreadFactory THREAD_FACTORY = new MyThreadFactory();

    // 拒绝策略 CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
    private static final RejectedExecutionHandler REJECTED_HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    // 线程池
    private static volatile ThreadPoolExecutor threadPool;

    static {
        threadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                WORK_QUEUE,
                THREAD_FACTORY,
                REJECTED_HANDLER
        );
    }

    public static void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

    public static void execute(FutureTask futureTask) {
        threadPool.execute(futureTask);
    }

    public static void cancel(FutureTask futureTask) {
        futureTask.cancel(true);
    }
}
