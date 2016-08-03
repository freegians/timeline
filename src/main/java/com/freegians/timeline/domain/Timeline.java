package com.freegians.timeline.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by freegians on 2016. 8. 1..
 */

@Entity (name = "timeline")
@Data
public class Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "USER_ID")
    private long userId;
    @Column(name = "WRITER_ID")
    private long followerId;
    @Column(name = "WRITER_NAME")
    private String writerName;
    @Column(name = "TIMELINE_TEXT")
    private String timelineText;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "ORIGINAL")
    private int original;
}
