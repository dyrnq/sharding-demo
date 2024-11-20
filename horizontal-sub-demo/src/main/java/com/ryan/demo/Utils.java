package com.ryan.demo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class Utils {

    public static DateTime getEndOfDayTimestamp(String time) {
        Date date = DateUtil.endOfDay(DateUtil.parse(time, "yyyy-MM-dd HH:mm:ss"));
        return DateUtil.beginOfSecond(date);
    }



    public static Date getTimestamp(String time) {
        Date date = DateUtil.parse(time, "yyyy-MM-dd HH:mm:ss");
        return date;
    }
}
