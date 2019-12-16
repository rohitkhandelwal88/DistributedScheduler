package com.rohit.DistributedScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ForkJoinPrint extends RecursiveAction {
    int beginIndex;
    int endIndex;


    ForkJoinPrint(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    @Override
    protected void compute() {
        List<String> list = new ArrayList<String>();
        List<ForkJoinPrint> tasks = new ArrayList<ForkJoinPrint>();
        if (endIndex - beginIndex >= 10) {
           createSubtasks();

        } else {
            for (int i = beginIndex; i <= endIndex; i++) {
                System.out.println(i);
            }

        }
    }

    private void createSubtasks() {

        int mid = (endIndex-beginIndex+1)/2;
        ForkJoinPrint subtask1 = new ForkJoinPrint(beginIndex, mid);
        ForkJoinPrint subtask2 = new ForkJoinPrint(mid+1, endIndex);

        invokeAll(subtask1,
                subtask2);

    }
}
