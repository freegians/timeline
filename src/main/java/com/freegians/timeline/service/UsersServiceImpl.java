package com.freegians.timeline.service;

import com.freegians.timeline.domain.UserRole;
import com.freegians.timeline.domain.Users;
import com.freegians.timeline.repository.IUserRoleRepository;
import com.freegians.timeline.repository.IUsersRepository;
import com.freegians.timeline.security.CurrentUser;
import com.freegians.timeline.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by freegians on 2016. 8. 1..
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService {

    private static final Logger LOG = LoggerFactory.getLogger(UsersServiceImpl.class);


    @Autowired
    IUsersRepository usersRepository;

    @Autowired
    IUserRoleRepository userRoleRepository;

    @Override
    public long countOfUsers() {
        return usersRepository.count();
    }

    @Override
    public Map<String, Object> createUser(String userName, String roleName) {
        Map<String, Object> result = new HashMap<>();

        Users users = new Users();
        users = createUser(userName);
        result.put("users", users);
        result.put("userRole", createUserRole(users.getUserId(), roleName));
        return result;
    }

    @Override
    public Users createUser(String userName) {
        TimeUtil timeUtil = new TimeUtil();

        Users users = new Users();
        users.setUserName(userName);
        // TODO 암호화 해야함
        users.setPassword(userName);
//        users.setPassword(new BCryptPasswordEncoder().encode(userName));
        users.setCreatedDate(timeUtil.getNowTimestamp());
        users.setLastUadate(timeUtil.getNowTimestamp());
        return usersRepository.save(users);
    }

    @Override
    public UserRole createUserRole(long userId, String roleName) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleName(roleName);
        return userRoleRepository.save(userRole);
    }

    @Override
    public CurrentUser getCurrentUser() {

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (UserDetails) principal;

            Users users = usersRepository.findByUserName(userDetails.getUsername());

            CurrentUser currentUser = new CurrentUser(users.getUserId(), userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            return currentUser;
        } catch (Exception e) {
            return null;
        }
    }
}
