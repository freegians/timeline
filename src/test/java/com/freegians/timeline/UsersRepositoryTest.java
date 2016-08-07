package com.freegians.timeline;

import com.freegians.timeline.domain.Users;
import com.freegians.timeline.repository.UsersRepository;
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
public class UsersRepositoryTest {
    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    @Autowired
    private UsersRepository usersRepository;

    @Before
    public void setUp() {
        em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE USERS").executeUpdate();
        em.getTransaction().commit();

        Users users = new Users("test", "test");
        Users createUser = usersRepository.save(users);
        assertEquals(createUser.getUserName(), users.getUserName());
    }

    @Test
    public void testSelectUser() {
        Users user = usersRepository.findByUserName("test");
        assertEquals(user.getUserName(), "test");
    }

    @Test
    public void testUpdateUser() {
        Users user = usersRepository.findByUserName("test");
        usersRepository.updateUserPassword("test2", user.getUserId());
        Users updateUser = usersRepository.findByUserName("test");
        assertEquals(updateUser.getPassword(), "test2");
    }

    @Test
    public void testDeleteUser() {
        Users user = usersRepository.findByUserName("test");
        usersRepository.delete(user);
        Users deleteUser = usersRepository.findByUserName("test");
        assertNull(deleteUser);
    }

    @After
    public void after() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE USERS").executeUpdate();
        em.getTransaction().commit();
        em.close();
        assertEquals(usersRepository.findAll().size(), 0);
    }
}
