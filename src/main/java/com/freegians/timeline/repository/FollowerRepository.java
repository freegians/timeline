package com.freegians.timeline.repository;

import com.freegians.timeline.domain.Follower;
import com.freegians.timeline.domain.PostQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface FollowerRepository extends JpaRepository <Follower, Long> {


    Follower findByUserIdAndFollowerId(Long userId, Long followerId);
}
