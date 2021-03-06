package com.freegians.timeline.service;

import com.freegians.timeline.domain.UserRole;
import com.freegians.timeline.domain.Users;
import com.freegians.timeline.security.CurrentUser;

import java.util.List;
import java.util.Map;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface UsersService {
    long countOfUsers();

    Map<String, Object> createUser(String userName, String roleName);

    Users createUser(String userName);

    UserRole createUserRole(long userId, String roleName);

    Users getUser(String userName);

    List<Users> getUserList();

    CurrentUser getCurrentUser();

    List<Users> getFollowerList(long userId);
}
