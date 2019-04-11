package com.yajun.green.common.thread;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: Jack Wang
 * Date: 14-5-7
 * Time: 上午11:03
 */
@Service("applicationThreadPool")
public class ApplicationThreadPool {

    private final static int TOTAL_THEAD = 15;

    public static ExecutorService exec = Executors.newFixedThreadPool(ApplicationThreadPool.TOTAL_THEAD);

    private static ThreadPoolTaskExecutor pool = null;

    static {
        pool = new ThreadPoolTaskExecutor();
        pool.setQueueCapacity(100);
        pool.setCorePoolSize(20);
        pool.setMaxPoolSize(50);
        pool.setKeepAliveSeconds(10000);
        pool.initialize();
    }

    public static void executeThread(Runnable task) {
        pool.execute(task);
    }
}
