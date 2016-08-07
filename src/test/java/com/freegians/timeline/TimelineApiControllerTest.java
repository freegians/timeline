package com.freegians.timeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.domain.Users;
import com.freegians.timeline.repository.PostQRepository;
import com.freegians.timeline.repository.TimelineRepository;
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
public class TimelineApiControllerTest {

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
    private TimelineRepository timelineRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PostQRepository postQRepository;

    private Timeline timeline;

    private MockMvc mock;

    @Before
    public void setUp() {
        this.mock = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();

        em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE TIMELINE").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE POST_Q").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE USERS").executeUpdate();
        em.getTransaction().commit();

        timeline = new Timeline(1L, 1L, "test", "test timeline text", 1);
        Timeline postTimeline = timelineRepository.save(timeline);
        assertEquals(postTimeline.getTimelineText(), timeline.getTimelineText());

        Users users = new Users("test", "test");
        Users createUser = usersRepository.save(users);
        assertEquals(createUser.getUserName(), users.getUserName());
    }

    @Test
    public void testGetTimelineAll() {
        try {
            ResultActions resultActions = mock.perform(MockMvcRequestBuilders.get("/api/timeline")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(timeline)));

            resultActions.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].timelineText", Matchers.is(timeline.getTimelineText())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPostTimeline() {
        try {
            ResultActions resultActions = mock.perform(MockMvcRequestBuilders.post("/api/timeline/post").with(user("test").password("test").roles("USER"))
                    .param("timelineText", timeline.getTimelineText())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(timeline)));

            resultActions.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.timelineText", Matchers.is(timeline.getTimelineText())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteTimeline() {
        try {
            ResultActions resultActions = mock.perform(MockMvcRequestBuilders.delete("/api/timeline/delete").with(user("test").password("test").roles("USER"))
                    .param("id", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(timeline)));

            resultActions.andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(true)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @After
    public void after() {

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE TIMELINE").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE POST_Q").executeUpdate();
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE USERS").executeUpdate();
        em.getTransaction().commit();

        em.close();

        assertEquals(timelineRepository.findAll().size(), 0);
        assertEquals(usersRepository.findAll().size(), 0);
        assertEquals(postQRepository.findAll().size(), 0);
    }
}
