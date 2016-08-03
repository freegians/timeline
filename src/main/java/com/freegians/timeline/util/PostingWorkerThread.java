package com.freegians.timeline.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by freegians on 2016. 8. 3..
 */
public class PostingWorkerThread implements Runnable {
    protected static final Logger LOG = LoggerFactory.getLogger(PostingWorkerThread.class);

    long seq;
    public PostingWorkerThread(long seq) {
        this.seq = seq;
    }

    @Override
    public void run() {

        LOG.info(this.seq + " Worker thread start.");
        try {
            LOG.info(this.seq + " Worker thread doing.");
            Thread.sleep(10000);
        }catch(Exception e) {
        }
        LOG.info(this.seq + " Worker thread end.");

        PostingMainThread.threads.remove(seq);

    }
}
