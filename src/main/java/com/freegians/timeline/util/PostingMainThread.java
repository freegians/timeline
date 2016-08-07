package com.freegians.timeline.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by freegians on 2016. 8. 3..
 * 
 * 
 */
public class PostingMainThread implements Runnable {
    protected static final Logger LOG = LoggerFactory.getLogger(PostingMainThread.class);

    long threadCount = 0;


    public static HashMap<Long, Thread> threads = new HashMap<Long, Thread>();


    int time;
    int limit;

    public PostingMainThread() {
        this.time = 1000;
        this.limit = 10;
    }


    public PostingMainThread(int time, int limit) {
        this.time = time;
        this.limit = limit;
    }

    @Override
    public void run() {

        LOG.info("Posting Main thread start.");
        try {
            while(true) {
                if(threads.size() <= 2) {
                    LOG.info("Posting Main thread doing.");
                    LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> " + threads.size());

                    threadCount++;
                    Thread t = new Thread(new PostingWorkerThread(threadCount));
                    t.start();

                    threads.put(threadCount, t);
                }
                Thread.sleep(this.time);
            }
//            Thread.sleep(1000);
        }catch(Exception e) {
        }
        LOG.info("Posting Main thread end.");

    }
}
