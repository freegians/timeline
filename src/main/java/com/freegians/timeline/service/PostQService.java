package com.freegians.timeline.service;

import com.freegians.timeline.domain.PostQ;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface PostQService {

    PostQ getPostQMin();

    PostQ savePostQ(PostQ postQ);

    DeferredResult<Long> workPostQ();
}
