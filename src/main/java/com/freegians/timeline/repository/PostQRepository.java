package com.freegians.timeline.repository;

import com.freegians.timeline.domain.PostQ;
import com.freegians.timeline.domain.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface PostQRepository extends JpaRepository <PostQ, Long> {

    @Query(value="SELECT ID, TIMELINE_ID FROM POST_Q ORDER BY ID ASC LIMIT 1", nativeQuery = true)
    PostQ findMinId();

    PostQ findById(long id);
}
