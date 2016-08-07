package com.freegians.timeline;

import com.freegians.timeline.domain.UserRole;
import com.freegians.timeline.repository.UserRoleRepository;
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
public class UserRoleRepositoryTest {
    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Before
    public void setUp() {
        em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE USER_ROLE").executeUpdate();
        em.getTransaction().commit();

        UserRole userRole = new UserRole(1L, "ROLE_USER");
        UserRole createUserRole = userRoleRepository.save(userRole);
        assertEquals(createUserRole.getRoleName(), userRole.getRoleName());
    }

    @Test
    public void testSelectUserRole() {
        List<UserRole> userRoleList = userRoleRepository.findByUserId(1L);
        assertEquals(userRoleList.size(), 1);
    }

    @Test
    public void testUpdateUserRole() {
        List<UserRole> userRoleList = userRoleRepository.findByUserId(1L);
        UserRole userRole = userRoleList.get(0);
        userRole.setRoleName("ROLE_ADMIN");
        UserRole updateUserRole = userRoleRepository.save(userRole);
        assertEquals(updateUserRole.getRoleName(), userRole.getRoleName());
    }

    @Test
    public void testDeleteUserRole() {
        List<UserRole> userRoleList = userRoleRepository.findByUserId(1L);
        UserRole userRole = userRoleList.get(0);
        userRoleRepository.delete(userRole.getId());
        assertEquals(userRoleRepository.findAll().size(), 0);
    }

    @After
    public void after() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE USER_ROLE").executeUpdate();
        em.getTransaction().commit();
        em.close();
        assertEquals(userRoleRepository.findAll().size(), 0);
    }
}
