package com.rohit.DistributedScheduler;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public class Main {

    @Autowired
    HazelcastInstance hazelcastInstance;


    public static void main(String[] args) throws InterruptedException {
        // Object of a class that has both produce()
        // and consume() methods
        final Job job = new Job();

        // Using Lambda Expression
        CompletableFuture<Void> producer = CompletableFuture.runAsync(() -> {
            // Simulate a long-running Job
            try {
                job.produce();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("I'll run in a separate thread than the main thread.");
        });

        CompletableFuture<Void> consumer = CompletableFuture.runAsync(() -> {
            // Simulate a long-running Job
            try {
                job.consume();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("I'll run in a separate thread than the main thread.");
        });

    }

    public static class Job {


        LinkedList<Task> list = new LinkedList<>();
        int capacity = 5;


        // Function called by producer thread
        public void produce() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    // producer thread waits while list
                    // is full
                    while (list.size() == capacity)
                        wait();


                    //create the task of template Task
                    Task mytask = new MyTask(1000);

                    list.add(mytask);

                    // notifies the consumer thread that
                    // now it can start consuming
                    notify();

                }
            }
        }

        public void consume() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    // consumer thread waits while list
                    // is empty
                    while (list.size() == 0)
                        wait();

                    // to retrive the first job in the list
                    Task myTask = list.removeFirst();


                    // Wake up producer thread
                    notify();

                    //run the task
                    myTask.run();
                }
            }
        }
    }
}
