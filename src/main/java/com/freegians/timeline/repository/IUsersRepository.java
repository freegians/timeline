package com.freegians.timeline.repository;

import com.freegians.timeline.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface IUsersRepository extends JpaRepository <Users, Long> {


}
