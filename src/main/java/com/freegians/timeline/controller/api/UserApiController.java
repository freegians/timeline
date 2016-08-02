package com.freegians.timeline.controller.api;

import com.freegians.timeline.controller.BaseController;
import com.freegians.timeline.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by freegians on 2016. 8. 1..
 */
@Controller
@RequestMapping("/api/user")
public class UserApiController extends BaseController{

    protected static final Logger LOG = LoggerFactory.getLogger(UserApiController.class);


    @Autowired
    UsersService usersService;

    /**
     * 유저 전체 카운트
     * @return
     */
    @RequestMapping(value = "/countOfUsers", method = RequestMethod.GET)
    @ResponseBody
    public Object getCountOfUsers() {
        try{
            return createSuccessResponse(usersService.countOfUsers());
        }catch (Exception e){
            return createFailureResponse("Failed to get count all of users.", e);
        }
    }


    /**
     * 유저 생성
     * @param userName
     * @return
     */
    @RequestMapping(value = "/{userName}", method = RequestMethod.PUT)
    @ResponseBody
    public Object putUser(
            @PathVariable("userName") String userName
    ) {
        try {
            return createSuccessResponse(usersService.createUser(userName, "USER"));
        } catch (Exception e) {
            return createFailureResponse("Fail to create user", e);
        }
    }




}
