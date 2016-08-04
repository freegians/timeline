package com.freegians.timeline.controller.api;

import com.freegians.timeline.controller.BaseController;
import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.service.PostQService;
import com.freegians.timeline.service.TimelineService;
import com.freegians.timeline.service.TimelineServiceImpl;
import com.freegians.timeline.service.UsersService;
import com.freegians.timeline.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by freegians on 2016. 8. 1..
 */
@Controller
@RequestMapping("/api/timeline")
public class TimelineApiController extends BaseController{

    protected static final Logger LOG = LoggerFactory.getLogger(TimelineApiController.class);


    @Autowired
    TimelineService timelineService;

    @Autowired
    UsersService usersService;

    @Autowired
    PostQService postQService;



    /**
     * 타임라인 리스트
     * @param userId
     * @param start
     * @param range
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Object getTimeline(
            @RequestParam(value = "userId", required=false, defaultValue = "0") Long userId,
            @RequestParam(value = "start", required=false, defaultValue = "0") Long start,
            @RequestParam(value = "range", required=false, defaultValue = "10") Integer range
    ) {
        try{

            if(start <= 0) {
                if(userId > 0) {
                    return createSuccessResponse(timelineService.getTimeline(userId));
                } else {
                    return createSuccessResponse(timelineService.getTimelineAllByOriginal());
                }
            } else {
                Map<String, Object> result = new HashMap<String, Object>();
                long next = start + range;
                result.put("next", next);

                start = start -1;

                if(userId > 0) {
                    result.put("timeline", timelineService.getTimeline(userId, start, range));
                } else {
                    result.put("timeline", timelineService.getTimelineAllByOriginal(start, range));
                }

                return createSuccessResponse(result);
            }
        }catch (Exception e){
            return createFailureResponse("Failed to get timeline.", e);
        }
    }


    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ResponseBody
    public Object postTimeline(
            @RequestParam(value = "timelineText", required=true) String timelineText
    ) {
        try{
            TimeUtil timeUtil = new TimeUtil();
            Timeline _timeline = new Timeline();
            _timeline.setUserId(usersService.getCurrentUser().getUserId());
            _timeline.setWriterId(usersService.getCurrentUser().getUserId());
            _timeline.setWriterName(usersService.getCurrentUser().getUsername());
            _timeline.setTimelineText(timelineText);
            _timeline.setCreatedDate(timeUtil.getNowTimestamp());
            _timeline.setOriginal(1);

            Timeline timeline = new Timeline();
            timeline = timelineService.postTimeline(_timeline);

            postQService.workPostQ();
            return createSuccessResponse(timeline);
        }catch (Exception e){
            return createFailureResponse("Failed to posting.", e);
        }
    }

//    /**
//     * 특정 유저 타임 라인 리스트
//     * @param userId
//     * @param start
//     * @param range
//     * @return
//     */
//    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
//    @ResponseBody
//    public Object getTimeline(
//            @PathVariable("userId") Long userId,
//            @RequestParam(value = "start", required=false, defaultValue = "0") Long start,
//            @RequestParam(value = "range", required=false, defaultValue = "10") Integer range
//    ) {
//        try{
//
//            if(start <= 0) {
//                return createSuccessResponse(timelineService.getTimeline(userId));
//            } else {
//                Map<String, Object> result = new HashMap<String, Object>();
//                long next = start + range;
//                start = start -1;
//
//                result.put("timeline", timelineService.getTimeline(userId, start, range));
//                result.put("next", next);
//                return createSuccessResponse(result);
//            }
//        }catch (Exception e){
//            return createFailureResponse("Failed to get timeline.", e);
//        }
//    }
//
//
//    /**
//     * 타임라인 리스트 전체
//     * @return
//     */
//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    @ResponseBody
//    public Object getTimelineAll(
//            @RequestParam(value = "start", required=false, defaultValue = "0") Long start,
//            @RequestParam(value = "range", required=false, defaultValue = "10") Integer range
//    ) {
//        try{
//
//            if(start <= 0) {
//                return createSuccessResponse(timelineService.getTimelineAll());
//            } else {
//                Map<String, Object> result = new HashMap<String, Object>();
//                long next = start + range;
//                start = start -1;
//
//                result.put("timeline", timelineService.getTimelineAll(start, range));
//                result.put("next", next);
//                return createSuccessResponse(result);
//            }
//        }catch (Exception e){
//            return createFailureResponse("Failed to get timeline.", e);
//        }
//    }




}
