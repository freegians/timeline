package com.freegians.timeline.service;

import com.freegians.timeline.model.UserRole;
import com.freegians.timeline.model.Users;

import java.util.Map;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface UsersService {
    long countOfUsers();

    Map<String, Object> createUser(String userName, String roleName);

    Users createUser(String userName);

    UserRole createUserRole(long userId, String roleName);
}
