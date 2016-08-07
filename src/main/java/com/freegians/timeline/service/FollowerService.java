package com.freegians.timeline.service;

import com.freegians.timeline.domain.Follower;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface FollowerService {

    Follower saveFollower(Follower follower);

    void deleteFollower(Long userId);

    Follower getFollower(Long userId, Long followerId);
}
