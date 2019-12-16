package com.rohit.DistributedScheduler.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ErrorHandler;

@Configuration
public class SchedulerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SchedulerConfiguration.class);
    @Value("${scheduler.threadPoolSize:4}")
    private int poolSize;
    @Value("${scheduler.awaitTerminationSeconds:0}")
    private int awaitTerminationSeconds;

    @Bean
    ThreadPoolTaskScheduler scheduler()
    {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(poolSize);
        scheduler.setRemoveOnCancelPolicy(true);
        if (awaitTerminationSeconds > 0) {
            scheduler.setWaitForTasksToCompleteOnShutdown(true);
            scheduler.setAwaitTerminationSeconds(awaitTerminationSeconds);
        }
        else
            scheduler.setWaitForTasksToCompleteOnShutdown(false);

        scheduler.setThreadGroupName("DistributedScheduler");
        scheduler.setThreadNamePrefix("__scheduler.");
        scheduler.setErrorHandler(new ErrorHandler() {

            @Override
            public void handleError(Throwable t) {
                log.error("-- Error stacktrace --", t);
            }
        });

        log.info("Scheduler configured with "+poolSize+" threads");
        return scheduler;
    }
}