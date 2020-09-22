package com.wisrc.crawler.webapp.utils;

import java.text.SimpleDateFormat;

public class Time {
    public static final String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis());
    }
}
