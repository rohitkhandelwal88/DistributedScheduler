package com.rohit.DistributedScheduler;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledFuture;

public class MyTask implements Task {

    private int number;

    private ScheduledFuture<?> future;
    public MyTask(int number)
    {
        this.number = number;
    }
    @Override
    public TaskResult run() {
        // Task logic
        ForkJoinPrint forkJoinPrint = new ForkJoinPrint(1, number);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.invoke(forkJoinPrint);
        return new TaskResult(TaskResult.Status.COMPLETED, "ERROR MESSAGE OR OTHER INFORMATION");

    }

    protected void destroy() {

    }

    @Override
    public boolean cancel()
    {
        if(!future.isCancelled())
        {
            boolean b = future.cancel(false);
            destroy();
            return b;
        }
        return false;
    }


}