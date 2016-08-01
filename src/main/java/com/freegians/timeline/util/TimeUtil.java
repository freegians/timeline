package com.freegians.timeline.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by freegians on 2015. 11. 23..
 */
public class TimeUtil {


    private static final Logger LOG = LoggerFactory.getLogger(TimeUtil.class);

    public Timestamp getNowTimestamp() {
        Calendar cal = Calendar.getInstance();
        Timestamp time = new java.sql.Timestamp(cal.getTimeInMillis());
        return time;
    }
}
