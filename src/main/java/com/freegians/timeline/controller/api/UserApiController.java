package com.freegians.timeline.controller.api;

import com.freegians.timeline.controller.BaseController;
import com.freegians.timeline.domain.Follower;
import com.freegians.timeline.service.FollowerService;
import com.freegians.timeline.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by freegians on 2016. 8. 1..
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController extends BaseController{

    protected static final Logger LOG = LoggerFactory.getLogger(UserApiController.class);


    @Autowired
    UsersService usersService;

    @Autowired
    FollowerService followerService;

    /**
     * 유저 전체 카운트
     * @return
     */
    @RequestMapping(value = "/countOfUsers", method = RequestMethod.GET)
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
    public Object putUser(
            @PathVariable("userName") String userName
    ) {
        try {
            return createSuccessResponse(usersService.createUser(userName, "ROLE_USER"));
        } catch (Exception e) {
            return createFailureResponse("Fail to create user", e);
        }
    }

    /**
     * 유저 전체 리스트
     * @return
     */
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public Object getUserListAll(
    ) {
        try {
            return createSuccessResponse(usersService.getUserList());
        } catch (Exception e) {
            return createFailureResponse("Fail to get user list", e);
        }
    }


    /**
     * 특정 유저의 팔로우 리스트
     * @param userId
     * @return
     */
    @RequestMapping(value = "/follower", method = RequestMethod.GET)
    public Object getFollowerList(
            @RequestParam(value = "userId", required=false, defaultValue = "0") Long userId
    ) {
        try {
            return createSuccessResponse(usersService.getFollowerList(userId));
        } catch (Exception e) {
            return createFailureResponse("Fail to follower list", e);
        }
    }


    /**
     * 특정 유저를 팔로잉 하기
     * @param userId
     * @return
     */
    @RequestMapping(value = "/following", method = RequestMethod.PUT)
    public Object putFollowing(
            @RequestParam(value = "userId", required=false, defaultValue = "0") Long userId
    ) {
        try {
            if(usersService.getCurrentUser() != null) {
                Follower follower = new Follower(userId, usersService.getCurrentUser().getUserId());
                return createSuccessResponse(followerService.saveFollower(follower));
            } else {
                return createFailureResponse("로그인이 필요합니다.");
            }
        } catch (Exception e) {
            return createFailureResponse("Fail to following", e);
        }
    }


    /**
     * 특정 유저를 언팔로잉 하기
     * @param userId
     * @return
     */
    @RequestMapping(value = "/unFollowing", method = RequestMethod.DELETE)
    public Object deleteFollowing(
            @RequestParam(value = "userId", required=false, defaultValue = "0") Long userId
    ) {
        try {
            if(usersService.getCurrentUser() != null) {

                Follower follower = followerService.getFollower(userId, usersService.getCurrentUser().getUserId());

                followerService.deleteFollower(follower.getId());
                return createSuccessResponse("success", "Success unfollowing");
            } else {
                return createFailureResponse("로그인이 필요합니다.");
            }
        } catch (Exception e) {
            return createFailureResponse("Fail to unfollowing", e);
        }
    }



}
