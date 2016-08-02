package com.freegians.timeline.controller.api;

import com.freegians.timeline.controller.BaseController;
import com.freegians.timeline.domain.Timeline;
import com.freegians.timeline.service.TimelineService;
import com.freegians.timeline.service.TimelineServiceImpl;
import com.freegians.timeline.service.UsersService;
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


    /**
     * 자신의 타임라인 리스트 전체
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public Object getTimeline(
            @RequestParam(value = "start", required=false, defaultValue = "0") Long start,
            @RequestParam(value = "range", required=false, defaultValue = "10") Integer range
    ) {
        try{

            if(start <= 0) {
                return createSuccessResponse(timelineService.getTimeline());
            } else {
                Map<String, Object> result = new HashMap<String, Object>();
                long next = start + range;
                start = start -1;

                result.put("timeline", timelineService.getTimeline(start, range));
                result.put("next", next);
                return createSuccessResponse(result);
            }
        }catch (Exception e){
            return createFailureResponse("Failed to get timeline.", e);
        }
    }

}
