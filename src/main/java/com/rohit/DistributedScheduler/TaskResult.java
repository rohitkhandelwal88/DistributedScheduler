package com.rohit.DistributedScheduler;

public class TaskResult {
    public    Status status;

    private String info;

    public TaskResult(Status status, String info) {
        this.status = status;
        this.info = info;
    }
    public enum Status {

        COMPLETED("completed"),
        FAILED("failed");

        private final String type;

        Status(String type) {
            this.type = type;
        }
    }
}
