package com.freegians.timeline.service;

import com.freegians.timeline.domain.Follower;
import com.freegians.timeline.domain.UserRole;
import com.freegians.timeline.domain.Users;
import com.freegians.timeline.repository.FollowerRepository;
import com.freegians.timeline.repository.UserRoleRepository;
import com.freegians.timeline.repository.UsersRepository;
import com.freegians.timeline.security.CurrentUser;
import com.freegians.timeline.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by freegians on 2016. 8. 1..
 */
@Service("followerService")
public class FollowerServiceImpl implements FollowerService {

    private static final Logger LOG = LoggerFactory.getLogger(FollowerServiceImpl.class);


    @Autowired
    FollowerRepository followerRepository;

    @Override
    public Follower saveFollower(Follower follower) {
        return followerRepository.save(follower);
    }

    @Override
    public void deleteFollower(Long userId) {
        followerRepository.delete(userId);
    }

    @Override
    public Follower getFollower(Long userId, Long followerId) {
        return followerRepository.findByUserIdAndFollowerId(userId, followerId);
    }

}
