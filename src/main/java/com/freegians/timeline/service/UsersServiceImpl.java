package com.freegians.timeline.service;

import com.freegians.timeline.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by freegians on 2016. 8. 1..
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Autowired
    IUsersRepository usersRepository;

    @Override
    public long countOfUsers() {
        return usersRepository.count();
    }
}
