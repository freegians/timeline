package com.freegians.timeline;

import com.freegians.timeline.domain.PostQ;
import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.domain.Users;
import com.freegians.timeline.repository.PostQRepository;
import com.freegians.timeline.service.AsyncService;
import com.freegians.timeline.service.PostQService;
import com.freegians.timeline.service.TimelineService;
import com.freegians.timeline.service.UsersService;
import com.freegians.timeline.util.PostingMainThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.Filter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.*;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@EnableAsync
public class TimelineApplication implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(TimelineApplication.class);

	@Autowired
	AsyncService asyncService;

	@Autowired
	PostQService postQService;

	@Autowired
	TimelineService timelineService;

	@Autowired
	UsersService usersService;

	@Autowired
	PostQRepository postQRepository;

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(TimelineApplication.class);
		springApplication.run(args);
	}


	// 인코딩 처리
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}

	// 인코딩 처리
	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

	@PreDestroy
	public void preDestroy() {
	}
	@PostConstruct
	public void postConstruct() {
	}


	// TODO 독립 프로세스로 따로 때내야 함
	@Override
	public void run(String... strings) throws Exception {
//		asyncService.testAsyncAnnotationForMethodsWithReturnType();

		testAsyncAnnotationForMethodsWithReturnType();

	}

	@Async("threadPoolTaskExecutor")
	public void asyncMethodWithVoidReturnType() {
		System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
	}

	@Async("threadPoolTaskExecutor")
	public Future<String> asyncMethodWithReturnType() {
		System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());
		try {
			Thread.sleep(1000 * 100);
			return new AsyncResult<String>("hello world !!!!");
		} catch (InterruptedException e) {
			//
		}
		return null;
	}

	public void testAsyncAnnotationForMethodsWithReturnType()
			throws InterruptedException, ExecutionException {

		Future<String> future = asyncMethodWithReturnType();
//        Future<String> future = workPostQ();

		while (true) {
			LOG.info(String.valueOf(future.isDone()));
			if (future.isDone()) {
				break;
			}
			System.out.println("Continue doing something else. ");
			Thread.sleep(1000);
		}
	}

	@Async("threadPoolTaskExecutor")
	public Future<String> workPostQ() {
		try {
			PostQ postQ = postQService.getPostQMin();

			if (postQ.getId() > 0) {
				Timeline timeline = timelineService.getTimelineById(postQ.getTimelineId());

				List<Users> usersList = usersService.getFollowerList(timeline.getWriterId());

				if (usersList.size() > 0) {
					for (Users user : usersList) {
						Timeline _timeline = new Timeline();
						_timeline.setUserId(user.getUserId());
						_timeline.setWriterId(timeline.getWriterId());
						_timeline.setWriterName(timeline.getWriterName());
						_timeline.setTimelineText(timeline.getTimelineText());
						_timeline.setCreatedDate(timeline.getCreatedDate());
						_timeline.setOriginal(0);
						timelineService.postTimeline(_timeline);
						Thread.sleep(1000L);
					}
					postQRepository.delete(postQ.getId());
					LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + postQ.getId());
				}
			}
			return new AsyncResult<String>("Success PostQ !!");
		} catch(InterruptedException e) {

		}
		return null;
	}
}
