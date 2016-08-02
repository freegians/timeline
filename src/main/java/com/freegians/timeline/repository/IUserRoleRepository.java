package com.freegians.timeline.repository;

import com.freegians.timeline.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface IUserRoleRepository extends JpaRepository <UserRole, Integer> {

    List<UserRole> findByUserId(long userId);
}
