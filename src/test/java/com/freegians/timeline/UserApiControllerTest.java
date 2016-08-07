package com.freegians.timeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freegians.timeline.domain.Follower;
import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.domain.Users;
import com.freegians.timeline.repository.FollowerRepository;
import com.freegians.timeline.repository.TimelineRepository;
import com.freegians.timeline.repository.UserRoleRepository;
import com.freegians.timeline.repository.UsersRepository;
import com.freegians.timeline.security.CurrentUser;
import com.freegians.timeline.security.WebSecurityConfig;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

/**
 * Created by freegians on 2016. 8. 5..
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TimelineApplication.class, WebSecurityConfig.class})
@WebAppConfiguration
@IntegrationTest("server.port=8888")
public class UserApiControllerTest {

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager em;

    @Autowired
    FilterChainProxy springSecurityFilterChain;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    FollowerRepository followerRepository;

    private Users users;

    private MockMvc mock;

    @Before
    public void setUp() {
        this.mock = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();

        em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE USERS").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE USER_ROLE").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE FOLLOWER").executeUpdate();
        em.getTransaction().commit();

        users = new Users("test", "test");
        Users createUser = usersRepository.save(users);
        assertEquals(createUser.getUserName(), users.getUserName());
    }

    @Test
    public void testCountOfUsers() {
        try {
            ResultActions resultActions = mock.perform(MockMvcRequestBuilders.get("/api/user/countOfUsers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(users)));

            resultActions.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateUser() {
        try {
//            users.setUserName("test2");
            ResultActions resultActions = mock.perform(MockMvcRequestBuilders.put("/api/user/test2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(users)));
            resultActions.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.users.userName", Matchers.is("test2")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetUserListAll() {
        try {
            ResultActions resultActions = mock.perform(MockMvcRequestBuilders.get("/api/user/listAll")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(users)));
            resultActions.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userName", Matchers.is(users.getUserName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFollower() {
        try {
            Users users1 = new Users("test1", "test1");
            Users createUser1 = usersRepository.save(users1);
            Users users2 = new Users("test2", "test2");
            Users createUser2 = usersRepository.save(users2);

            Follower follower = new Follower(1L, 2L);
            followerRepository.save(follower);
            follower = new Follower(1L, 3L);
            followerRepository.save(follower);

            ResultActions resultActions = mock.perform(MockMvcRequestBuilders.get("/api/user/follower")
                    .param("userId", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(users)));

            resultActions.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userName", Matchers.is(createUser1.getUserName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].userName", Matchers.is(createUser2.getUserName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testPutFollowingUnFollowing() {
        try {
            Users users1 = new Users("test1", "test1");
            Users createUser1 = usersRepository.save(users1);

            ResultActions resultActions = mock.perform(MockMvcRequestBuilders.put("/api/user/following").with(user("test").password("test").roles("USER"))
                    .param("userId", String.valueOf(createUser1.getUserId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(createUser1)));

            resultActions.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId", Matchers.is((int) createUser1.getUserId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.followerId", Matchers.is(1)));


            ResultActions resultActions2 = mock.perform(MockMvcRequestBuilders.delete("/api/user/unFollowing").with(user("test").password("test").roles("USER"))
                    .param("userId", String.valueOf(createUser1.getUserId()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(createUser1)));

            resultActions2.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(true)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is("Success unfollowing")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @After
    public void after() {

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE USERS").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE USER_ROLE").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE FOLLOWER").executeUpdate();
        em.getTransaction().commit();

        em.close();

        assertEquals(usersRepository.findAll().size(), 0);
        assertEquals(userRoleRepository.findAll().size(), 0);
        assertEquals(followerRepository.findAll().size(), 0);

    }
}
