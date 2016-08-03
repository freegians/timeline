package com.freegians.timeline.service;

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

    @Override
    public List<Timeline> getTimelineAll() {
        return timelineRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "createdDate"), new Sort.Order(Sort.Direction.DESC, "id")));
    }

    @Override
    public List<Timeline> getTimelineAll(long start, int range) {
        return timelineRepository.findOrderByCreatedDateDescIdDesc(start, range);
    }

    @Override
    public List<Timeline> getTimeline(long userId) {
        return timelineRepository.findByUserIdOrderByCreatedDateDescIdDesc(userId);
    }

    @Override
    public List<Timeline> getTimeline(long userId, long start, int range) {
        return timelineRepository.findByUserIdOrderByCreatedDateDescIdDesc(userId, start, range);
    }
}
