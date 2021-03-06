package com.freegians.timeline.controller.api;

import com.freegians.timeline.controller.BaseController;
import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.service.PostQService;
import com.freegians.timeline.service.TimelineService;
import com.freegians.timeline.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by freegians on 2016. 8. 1..
 */
@RestController
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

    /**
     * 글쓰기
     * @param timelineText
     * @return
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Object postTimeline(
            @RequestParam(value = "timelineText", required=true) String timelineText
    ) {
        try{

            Timeline postTimeline = new Timeline(usersService.getCurrentUser().getUserId(), usersService.getCurrentUser().getUserId(), usersService.getCurrentUser().getUsername(), timelineText, 1);

            Timeline timeline = timelineService.postTimeline(postTimeline);


            // TODO 독립 프로세스를 이용해서 처리하도록 분리 해야 함함
           postQService.workPostQ();

            return createSuccessResponse(timeline);
        }catch (Exception e){
            return createFailureResponse("Failed to posting.", e);
        }
    }


    /**
     * 글 삭제
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Object deleteTimeline(
            @RequestParam(value = "id", required=true) Long id
    ) {
        try{

            if(usersService.getCurrentUser() != null) {
                Timeline timeline = timelineService.getTimelineById(id);
                if(usersService.getCurrentUser().getUserId() == timeline.getWriterId()) {
                    timelineService.deleteTimelineById(id);
                    return createSuccessResponse("success", "Success to delete timeline");
                } else {
                    return createFailureResponse("자신의 글만 삭제 가능 합니다.");
                }
            } else {
                return createFailureResponse("로그인이 필요합니다.");
            }
        } catch (Exception e){
            return createFailureResponse("Failed to delete timeline.", e);
        }
    }

}
