package com.rohit.DistributedScheduler;

public interface Task {
    boolean cancel();

    TaskResult run();
}
