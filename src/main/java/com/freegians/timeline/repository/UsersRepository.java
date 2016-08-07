package com.freegians.timeline.repository;

import com.freegians.timeline.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by freegians on 2016. 8. 1..
 */
@Transactional
public interface UsersRepository extends JpaRepository <Users, Long> {

    Users findByUserName(String username);

    @Query(value="SELECT u.USER_ID as USER_ID, u.USER_NAME as USER_NAME, u.PASSWORD as PASSWORD, u.CREATED_DATE as CREATED_DATE, u.LAST_UPDATE as LAST_UPDATE FROM FOLLOWER f, USERS u WHERE f.USER_ID = ?1 and f.FOLLOWER_ID = u.USER_ID", nativeQuery = true)
    List<Users> findByUserId(long userId);

    @Modifying
    @Query(value = "UPDATE USERS SET PASSWORD = ?1 WHERE USER_ID= ?2", nativeQuery = true)
    void updateUserPassword(String password, Long userId);
}
