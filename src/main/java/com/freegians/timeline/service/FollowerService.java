package com.freegians.timeline.service;

import com.freegians.timeline.domain.Follower;
import com.freegians.timeline.domain.UserRole;
import com.freegians.timeline.domain.Users;
import com.freegians.timeline.security.CurrentUser;

import java.util.List;
import java.util.Map;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface FollowerService {

    Follower saveFollower(Follower follower);

    void deleteFollower(Long userId);

    Follower getFollower(Long userId, Long followerId);
}
