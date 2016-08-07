package com.freegians.timeline.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by freegians on 2016. 8. 1..
 */

@Entity (name = "follower")
@Data
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "USER_ID")
    private long userId;
    @Column(name = "FOLLOWER_ID")
    private long followerId;

    public Follower() {}

    public Follower(Long userId, Long followerId) {
        super();
        this.userId = userId;
        this.followerId = followerId;
    }
}
