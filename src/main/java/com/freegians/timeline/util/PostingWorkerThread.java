package com.freegians.timeline.util;

import com.freegians.timeline.domain.PostQ;
import com.freegians.timeline.repository.PostQRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by freegians on 2016. 8. 3..
 */
public class PostingWorkerThread implements Runnable {
    protected static final Logger LOG = LoggerFactory.getLogger(PostingWorkerThread.class);

    @Autowired
    PostQRepository postQRepository;

    long seq;
    public PostingWorkerThread(long seq) {
        this.seq = seq;
    }

    @Override
    public void run() {

        LOG.info(this.seq + " Worker thread start.");
        try {
            LOG.info(this.seq + " Worker thread doing.");
//            PostQService postQService = new PostQServiceImpl();
//            postQService.workPostQ();

//            LOG.info("################>>>>>>>>>>>>>>>>>>>>>>>" + num);


            PostQ postQ = postQRepository.findMinId();
            LOG.info("########################" + postQ.getId());
            Thread.sleep(10000);
        }catch(Exception e) {
        }
        LOG.info(this.seq + " Worker thread end.");

        PostingMainThread.threads.remove(seq);

    }
}
