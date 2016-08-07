package com.freegians.timeline.service;

import com.freegians.timeline.domain.Timeline;

import java.util.List;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface TimelineService {

    List<Timeline> getTimelineAll();

    List<Timeline> getTimelineAllByOriginal();

    List<Timeline> getTimelineAll(long start, int range);

    List<Timeline> getTimelineAllByOriginal(long start, int range);

    List<Timeline> getTimeline(long userId);

    List<Timeline> getTimeline(long userId, long start, int range);

    Timeline postTimeline(Timeline timeline);

    Timeline getTimelineById(long id);

    void deleteTimelineById(long id);
}
