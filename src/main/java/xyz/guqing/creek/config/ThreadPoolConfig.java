package xyz.guqing.creek.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.guqing.creek.model.constant.CreekConstant;
import xyz.guqing.creek.model.properties.ThreadPoolProperties;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author guqing
 * @date 2020-06-03
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolConfig {

    @Bean(CreekConstant.ASYNC_POOL)
    public ThreadPoolExecutor coreThreadPoolExecutor(ThreadPoolProperties poolProperties) {

        // 创建线程阻塞队列
        LinkedBlockingDeque<Runnable> runnableLinkedBlockingDeque = new LinkedBlockingDeque<>(poolProperties.getQueueSize());

        // 创建带名字的线程工厂,使新创建的线程都有名字
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("travelPath-pool-%d").build();

        // 创建线程池
        return new ThreadPoolExecutor(poolProperties.getCorePoolSize(),
                poolProperties.getMaximumPoolSize(),
                poolProperties.getKeepAliveTime(), TimeUnit.MINUTES,
                runnableLinkedBlockingDeque, namedThreadFactory);
    }
}
