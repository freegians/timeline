package com.freegians.timeline.repository;

import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.domain.Users;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by freegians on 2016. 8. 1..
 */
public interface TimelineRepository extends JpaRepository <Timeline, Long> {

    List<Timeline> findByOriginalOrderByCreatedDateDescIdDesc(int original);

    List<Timeline> findByUserIdOrderByCreatedDateDescIdDesc(long userId);

    @Query(value="SELECT ID, USER_ID, WRITER_ID, WRITER_NAME, TIMELINE_TEXT, CREATED_DATE FROM TIMELINE WHERE USER_ID = ?1 ORDER BY CREATED_DATE DESC, ID DESC LIMIT ?2, ?3", nativeQuery = true)
    List<Timeline> findByUserIdOrderByCreatedDateDescIdDesc(long userId, long start, int range);

    @Query(value="SELECT ID, USER_ID, WRITER_ID, WRITER_NAME, TIMELINE_TEXT, CREATED_DATE FROM TIMELINE ORDER BY CREATED_DATE DESC, ID DESC LIMIT ?1, ?2", nativeQuery = true)
    List<Timeline> findOrderByCreatedDateDescIdDesc(long start, int range);

    @Query(value="SELECT ID, USER_ID, WRITER_ID, WRITER_NAME, TIMELINE_TEXT, CREATED_DATE FROM TIMELINE WHERE ORIGINAL = 1 ORDER BY CREATED_DATE DESC, ID DESC LIMIT ?1, ?2", nativeQuery = true)
    List<Timeline> findByOriginalOrderByCreatedDateDescIdDesc(long start, int range);
}
