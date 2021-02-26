package com.xyzcorp.loom.virtualthread;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class E5_VirtualThreadGoAheadAndBlock {
    public static void main(String[] args)
        throws InterruptedException {

        ThreadFactory tf = Thread.builder().virtual().factory();
        ExecutorService executorService = Executors.newThreadExecutor(tf);

        Stream<Callable<Integer>> callableStream = Stream.iterate(0,
            integer -> integer + 1).map(i -> () -> {
            System.out.format("Process(%d) Started: inside of Thread %s\n",
                i, Thread.currentThread());
            Thread.sleep(5000); //Block
            System.out.format("Process(%d) Finished: Inside of Thread %s\n"
                , i, Thread.currentThread());
            return 100;
        });

        List<Callable<Integer>> callables = callableStream.limit(10).toList();
        executorService.invokeAll(callables);
    }
}
