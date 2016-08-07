package com.freegians.timeline.service;

import com.freegians.timeline.domain.PostQ;
import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.domain.Users;
import com.freegians.timeline.repository.PostQRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by freegians on 2016. 8. 1..
 */
@Service("postQService")
public class PostQServiceImpl implements PostQService {

    private static final Logger LOG = LoggerFactory.getLogger(PostQServiceImpl.class);

    @Autowired
    PostQRepository postQRepository;

    @Autowired
    TimelineService timelineService;

    @Autowired
    UsersService usersService;

    @Override
    public PostQ getPostQMin() {
        return postQRepository.findMinId();
    }

    @Override
    public PostQ savePostQ(PostQ postQ) {
        return postQRepository.save(postQ);
    }

    @Override
    public void workPostQ() {
        try {
            PostQ postQ = getPostQMin();

            if (postQ.getId() > 0) {
                Timeline timeline = timelineService.getTimelineById(postQ.getTimelineId());

                List<Users> usersList = usersService.getFollowerList(timeline.getWriterId());

                if (usersList.size() > 0) {
                    for (Users user : usersList) {
                        Timeline _timeline = new Timeline(user.getUserId(), timeline.getWriterId(), timeline.getWriterName(), timeline.getTimelineText(), 0);
                        timelineService.postTimeline(_timeline);
//                        Thread.sleep(1000000L);
                    }
                    postQRepository.delete(postQ.getId());
                }
            }
        } catch(Exception e) {
            e.getMessage();
        }
    }
}
