package com.freegians.timeline.controller.web;

import com.freegians.timeline.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@Controller("mainController")
//public class MainController extends BaseController {
public class MainController {
	private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

	@Autowired
	UsersService usersService;


	/**
	 * 인덱스 페이지
	 * @return
     */
	@RequestMapping("/")
	public ModelAndView hello() {
		ModelAndView mav = new ModelAndView();
		if(usersService.getCurrentUser() != null) {
			mav.addObject("userId", usersService.getCurrentUser().getUserId());
			mav.addObject("userName", usersService.getCurrentUser().getUsername());
			mav.addObject("role", usersService.getCurrentUser().getAuthorities());
		}
		mav.setViewName("index");
		return mav;
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}


//	@RequestMapping(value = "/main", method = RequestMethod.GET)
//	public ModelAndView getWorkbenchView(
//			@RequestParam(value = "wb", required=false) String wb
//			) {
//		ModelAndView mav = new ModelAndView();
//
//		mav.setViewName("web/main");
//		return mav;
//	}

}
