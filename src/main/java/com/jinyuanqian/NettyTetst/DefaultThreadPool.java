package com.jinyuanqian.NettyTetst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class DefaultThreadPool implements ThreadPool{

    // 线程池最大限制数    maximumPoolSize
    private static final int MAX_WORKER_NUMBERS = 10;

    //队列
    private LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    // 工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());

    DefaultThreadPool(int num){
            for(int i = 0 ; i< num ; i++){
                Worker work = new Worker();
                workers.add(work);
                Thread thread = new Thread(work);
                thread.start();
            }

    }


    @Override
    public void execute(Runnable job) {
        if( job == null){
            return;
        }
        synchronized (taskQueue){
            System.out.println("方法执行");
            taskQueue.add(job);
            taskQueue.notify();
        }
    }

    class Worker implements Runnable{

        private volatile boolean running = true;


        @Override
        public void run() {
            while (running) {
                    Runnable runnable = null;
                    synchronized (taskQueue){
                        if(taskQueue.isEmpty()){
                            try {
                                taskQueue.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }

                        }
                        runnable =  taskQueue.poll();

                    }
                if(runnable != null){
                    runnable.run();
                }

            }

        }
    }
    public static void main(String[] args) {
        ExecutorService ex = Executors.newFixedThreadPool(10);

        ThreadPool pool = new DefaultThreadPool(2);
        pool.execute(() -> {
            for(int j = 0 ;j <= 1000;j++){
                System.out.println(j);
                System.out.println(Thread.currentThread().getName());
            }

        });
        pool.execute(() -> {System.out.println("帅");
            for(int j = 0 ;j <= 1000;j++){
                System.out.println("帅 " + j);
                System.out.println(Thread.currentThread().getName());
            }

        });
        pool.execute(() -> {System.out.println("10000");
            System.out.println(Thread.currentThread().getName());

        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {

        }
    }

}