package com.freegians.timeline.service;

import com.freegians.timeline.domain.PostQ;
import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.repository.TimelineRepository;
import com.freegians.timeline.security.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by freegians on 2016. 8. 1..
 */
@Service("timelineService")
public class TimelineServiceImpl implements TimelineService {

    private static final Logger LOG = LoggerFactory.getLogger(TimelineServiceImpl.class);

    @Autowired
    TimelineRepository timelineRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    PostQService postQService;

    @Override
    public List<Timeline> getTimelineAll() {
        return timelineRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "createdDate"), new Sort.Order(Sort.Direction.DESC, "id")));
    }

    @Override
    public List<Timeline> getTimelineAllByOriginal() {
        int original = 1;
        return timelineRepository.findByOriginalOrderByCreatedDateDescIdDesc(original);
    }

    @Override
    public List<Timeline> getTimelineAll(long start, int range) {
        return timelineRepository.findOrderByCreatedDateDescIdDesc(start, range);
    }

    @Override
    public List<Timeline> getTimelineAllByOriginal(long start, int range) {
        return timelineRepository.findByOriginalOrderByCreatedDateDescIdDesc(start, range);
    }

    @Override
    public List<Timeline> getTimeline(long userId) {
        return timelineRepository.findByUserIdOrderByCreatedDateDescIdDesc(userId);
    }

    @Override
    public List<Timeline> getTimeline(long userId, long start, int range) {
        return timelineRepository.findByUserIdOrderByCreatedDateDescIdDesc(userId, start, range);
    }


    @Override
    public Timeline postTimeline(Timeline timeline) {
        Timeline timelineResult = new Timeline();
        timelineResult = timelineRepository.save(timeline);


        if(timeline.getOriginal() == 1) {
            PostQ postQ = new PostQ();
            postQ.setTimelineId(timelineResult.getId());

            postQService.savePostQ(postQ);
        }

        return timelineResult;
    }

    @Override
    public Timeline getTimelineById(long id) {
        return timelineRepository.findOne(id);
    }
}
