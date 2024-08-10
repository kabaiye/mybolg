package com.kabaiye.util;

import jakarta.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

@Nullable
public class TimeUtil {
    // yyyy-MM-dd
    public static String dateToStringSimply(Date date){
        // 创建SimpleDateFormat对象，指定日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 输出格式化后的日期
        return date == null ? null: sdf.format(date);
    }

    // 2019-10-23T16:29:49
    public static String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return date == null ? null: sdf.format(date);
    }

    public static Date StringToDateSimply(String StringDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(StringDate);
        } catch (Exception ignored) {
        }
        return date;
    }

    public static Date StringToDate(String StringDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(StringDate);
        } catch (Exception ignored) {}
        return date;
    }
}
