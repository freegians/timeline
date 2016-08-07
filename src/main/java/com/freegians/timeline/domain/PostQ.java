package com.freegians.timeline.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by freegians on 2016. 8. 1..
 */

@Entity (name = "postQ")
@Table(name = "POST_Q")
@Data
public class PostQ {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "TIMELINE_ID")
    private long timelineId;

    public PostQ() {}

    public PostQ(Long timelineId) {
        super();
        this.timelineId = timelineId;
    }
}
