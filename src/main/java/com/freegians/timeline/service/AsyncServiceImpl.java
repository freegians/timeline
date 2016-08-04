package com.freegians.timeline.service;

import com.freegians.timeline.domain.PostQ;
import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.domain.Users;
import com.freegians.timeline.repository.PostQRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by freegians on 2016. 8. 4..
 */
@Service("asyncService")
public class AsyncServiceImpl implements AsyncService {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Autowired
    PostQService postQService;

    @Autowired
    TimelineService timelineService;

    @Autowired
    UsersService usersService;

    @Autowired
    PostQRepository postQRepository;

    @Async("threadPoolTaskExecutor")
    public void asyncMethodWithVoidReturnType() {
        System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
    }

    @Async("threadPoolTaskExecutor")
    public Future<String> asyncMethodWithReturnType() {
        System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000 * 100);
            return new AsyncResult<String>("hello world !!!!");
        } catch (InterruptedException e) {
            //
        }
        return null;
    }

    @Override
    public void testAsyncAnnotationForMethodsWithReturnType()
            throws InterruptedException, ExecutionException {

        Future<String> future = asyncMethodWithReturnType();
//        Future<String> future = workPostQ();

        while (true) {
            LOG.info(String.valueOf(future.isDone()));
            if (future.isDone()) {
                break;
            }
            System.out.println("Continue doing something else. ");
            Thread.sleep(1000);
        }
    }


    @Async("threadPoolTaskExecutor")
    public Future<String> workPostQ() {
        try {
            PostQ postQ = postQService.getPostQMin();

            if (postQ.getId() > 0) {
                Timeline timeline = timelineService.getTimelineById(postQ.getTimelineId());

                List<Users> usersList = usersService.getFollowerList(timeline.getWriterId());

                if (usersList.size() > 0) {
                    for (Users user : usersList) {
                        Timeline _timeline = new Timeline();
                        _timeline.setUserId(user.getUserId());
                        _timeline.setWriterId(timeline.getWriterId());
                        _timeline.setWriterName(timeline.getWriterName());
                        _timeline.setTimelineText(timeline.getTimelineText());
                        _timeline.setCreatedDate(timeline.getCreatedDate());
                        _timeline.setOriginal(0);
                        timelineService.postTimeline(_timeline);
                        Thread.sleep(1000L);
                    }
                    postQRepository.delete(postQ.getId());
                    LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + postQ.getId());
                }
            }
            return new AsyncResult<String>("Success PostQ !!");
        } catch(InterruptedException e) {

        }
        return null;
    }
}
