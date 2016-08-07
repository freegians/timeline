package com.freegians.timeline;

import com.freegians.timeline.domain.PostQ;
import com.freegians.timeline.repository.PostQRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by freegians on 2016. 8. 5..
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TimelineApplication.class)
@WebAppConfiguration
public class PostQRepositoryTest {
    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    @Autowired
    private PostQRepository postQRepository;

    @Before
    public void setUp() {
        em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE POST_Q").executeUpdate();
        em.getTransaction().commit();

        PostQ postQ = new PostQ(1L);
        PostQ createPostQ = postQRepository.save(postQ);
        assertEquals(createPostQ.getTimelineId(), postQ.getTimelineId());

    }

    @Test
    public void testSelectPostQ() {
        PostQ postQ = postQRepository.findOne(1L);
        assertEquals(postQ.getTimelineId(), 1L);
    }

    @Test
    public void testUpdatePostQ() {
        PostQ postQ = postQRepository.findOne(1L);
        postQ.setTimelineId(2L);
        PostQ updatePostQ = postQRepository.save(postQ);
        assertEquals(updatePostQ.getTimelineId(), postQ.getTimelineId());
    }

    @Test
    public void testDeleteUserPostQ() {
        PostQ postQ = postQRepository.findOne(1L);
        postQRepository.delete(postQ);
        assertNull(postQRepository.findOne(1L));
    }

    @After
    public void after() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE POST_Q").executeUpdate();
        em.getTransaction().commit();
        em.close();
        assertEquals(postQRepository.findAll().size(), 0);
    }
}
