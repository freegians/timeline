package com.freegians.timeline.service;

import com.freegians.timeline.domain.UserRole;
import com.freegians.timeline.domain.Users;
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
@Service("usersService")
public class UsersServiceImpl implements UsersService {

    private static final Logger LOG = LoggerFactory.getLogger(UsersServiceImpl.class);


    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

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

        Users users = new Users(userName, userName);
        // TODO 패스워드 암호화 해야함
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
    public Users getUser(String userName) {
        return usersRepository.findByUserName(userName);
    }

    @Override
    public List<Users> getUserList() {
        return usersRepository.findAll();
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

    @Override
    public List<Users> getFollowerList(long userId) {
        return usersRepository.findByUserId(userId);
    }
}
