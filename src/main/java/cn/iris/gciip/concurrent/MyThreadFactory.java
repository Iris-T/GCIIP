package cn.iris.gciip.concurrent;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName MyThreadFactory
 * @Description 自定义线程工厂
 * @author Iris
 * @date 2022/8/29 13:33
 */

public class MyThreadFactory implements ThreadFactory {
    private final AtomicInteger threadId = new AtomicInteger();

    /**
     * 创建新线程，并格式化名称
     * @param r 新线程
     * @return 格式化名称的线程
     */
    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r, "ThreadPoolUtil" + threadId.getAndIncrement());
    }
}
