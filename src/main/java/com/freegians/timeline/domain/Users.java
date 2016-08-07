package com.freegians.timeline.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by freegians on 2016. 8. 1..
 */

@Entity (name = "users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private long userId;
    @Column(name = "USER_NAME")
    private String userName;
    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", insertable = false, updatable = false)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE", insertable = false, updatable = false)
    private Date lastUpdate;


    public Users() {}

    public Users(Long userId, String userName, String password, Date createdDate, Date lastUpdate) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
    }

    public Users(String userName, String password, Date createdDate, Date lastUpdate) {
        super();
        this.userName = userName;
        this.password = password;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
    }

    public Users(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }
}
