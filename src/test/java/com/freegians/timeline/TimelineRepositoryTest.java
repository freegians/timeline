package com.freegians.timeline;

import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.repository.TimelineRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by freegians on 2016. 8. 5..
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TimelineApplication.class)
@WebAppConfiguration
public class TimelineRepositoryTest {
    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    @Autowired
    private TimelineRepository timelineRepository;

    @Before
    public void setUp() {
        em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE TIMELINE").executeUpdate();
        em.getTransaction().commit();

        Timeline timeline = new Timeline(1L, 1L, "test", "test timeline text", 1);
        Timeline postTimeline = timelineRepository.save(timeline);
        assertEquals(postTimeline.getTimelineText(), timeline.getTimelineText());
    }

    @Test
    public void testSelectTimeline() {
        List<Timeline> timelineList = timelineRepository.findByUserIdOrderByCreatedDateDescIdDesc(1L);
        assertEquals(timelineList.size(), 1);
    }

    @Test
    public void testUpdateTimeline() {
        List<Timeline> timeline = timelineRepository.findByUserIdOrderByCreatedDateDescIdDesc(1L);
        Timeline getTimeline = timeline.get(0);
        getTimeline.setTimelineText("test2 timeline text");
        Timeline updateTimeline = timelineRepository.save(getTimeline);
        assertEquals(updateTimeline.getTimelineText(), getTimeline.getTimelineText());
    }

    @Test
    public void testDeleteTimeline() {
        List<Timeline> timeline = timelineRepository.findByUserIdOrderByCreatedDateDescIdDesc(1L);
        Timeline getTimeline = timeline.get(0);
        timelineRepository.delete(getTimeline.getId());
        assertEquals(timelineRepository.findAll().size(), 0);
    }

    @After
    public void after() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE TIMELINE").executeUpdate();
        em.getTransaction().commit();
        em.close();
        assertEquals(timelineRepository.findAll().size(), 0);
    }
}
