package com.freegians.timeline.service;

import com.freegians.timeline.domain.PostQ;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface AsyncService {

    void testAsyncAnnotationForMethodsWithReturnType()
            throws InterruptedException, ExecutionException;
}
