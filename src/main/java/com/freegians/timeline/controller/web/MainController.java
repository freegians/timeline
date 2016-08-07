package com.freegians.timeline.controller.web;

import com.freegians.timeline.domain.Users;
import com.freegians.timeline.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller("mainController")
public class MainController {
	private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

	@Autowired
	UsersService usersService;


	/**
	 * 메인 페이지
	 * @return
     */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(
	) {
		ModelAndView mav = new ModelAndView();
	if(usersService.getCurrentUser() != null) {
		return (ModelAndView)new ModelAndView("redirect:/" + usersService.getCurrentUser().getUsername() );
	}
		mav.setViewName("index");
		return mav;
	}

	/**
	 * 특정 유저 메인 페이지
	 * @param userName
	 * @return
     */
	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public ModelAndView user(
			@PathVariable("userName") String userName
	) {
		ModelAndView mav = new ModelAndView();
		if(userName != null) {
			Users user = usersService.getUser(userName);
			mav.addObject("userId", user.getUserId());
			mav.addObject("userName", user.getUserName());
		}
		mav.setViewName("index");
		return mav;
	}

	/**
	 * 로그인 페이지
	 * @return
     */
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * 회원 가입 페이지
	 * @return
     */
	@RequestMapping("/signup")
	public String signUp() {
		return "signup";
	}

}
