package com.freegians.timeline.service;

import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.domain.UserRole;
import com.freegians.timeline.domain.Users;
import com.freegians.timeline.security.CurrentUser;

import java.util.List;
import java.util.Map;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface TimelineService {

    List<Timeline> getTimeline();

    List<Timeline> getTimeline(long start, int range);

    List<Timeline> getTimeline(long userId);
}
