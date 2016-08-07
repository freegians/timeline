package com.freegians.timeline;

import com.freegians.timeline.domain.Follower;
import com.freegians.timeline.repository.FollowerRepository;
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
public class FollowerRepositoryTest {
    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    @Autowired
    private FollowerRepository followerRepository;

    @Before
    public void setUp() {
        em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE FOLLOWER").executeUpdate();
        em.getTransaction().commit();

        Follower follower = new Follower(1L, 2L);
        Follower createFollower = followerRepository.save(follower);
        assertEquals(createFollower.getUserId(), follower.getUserId());

    }

    @Test
    public void testSelectFollower() {
        Follower follower = followerRepository.findOne(1L);
        assertEquals(follower.getFollowerId(), 2L);
    }

    @Test
    public void testUpdateFollower() {
        Follower follower = followerRepository.findOne(1L);
        follower.setFollowerId(3L);
        Follower updateFollower = followerRepository.save(follower);
        assertEquals(updateFollower.getFollowerId(), follower.getFollowerId());
    }

    @Test
    public void testDeleteUserFollower() {
        Follower follower = followerRepository.findOne(1L);
        followerRepository.delete(1L);
        assertNull(followerRepository.findOne(1L));
    }

    @After
    public void after() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE FOLLOWER").executeUpdate();
        em.getTransaction().commit();
        em.close();
        assertEquals(followerRepository.findAll().size(), 0);
    }
}
