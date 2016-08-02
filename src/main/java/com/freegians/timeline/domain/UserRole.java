package com.freegians.timeline.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by freegians on 2016. 6. 1..
 */
@Entity (name = "userRole")
@Data
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    @Column(name = "USER_ID")
    private long userId;
    @Column(name = "ROLE_NAME")
    private String roleName;

    public UserRole(){}
    public UserRole(int id, long userId, String roleName) {
        super();
        this.id = id;
        this.userId = userId;
        this.roleName = roleName;
    }

}
