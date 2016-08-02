package com.freegians.timeline.repository;

import com.freegians.timeline.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface UsersRepository extends JpaRepository <Users, Long> {

    Users findByUserName(String username);
}
