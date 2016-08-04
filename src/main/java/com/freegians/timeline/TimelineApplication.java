package com.freegians.timeline;

import com.freegians.timeline.util.PostingMainThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class TimelineApplication {
	private static final Logger LOG = LoggerFactory.getLogger(TimelineApplication.class);



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

	@PostConstruct
	public void postConstruct() {
//			Thread t = new Thread(new PostingMainThread(1000, 2));
//			t.start();

	}

}
