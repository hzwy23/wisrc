package com.wisrc.replenishment.webapp.utils;

import org.springframework.cglib.beans.BeanMap;

import java.sql.Date;
import java.util.*;

public class ServiceUtils {
    public static Map LinkedHashMapToMap(Object obj) {
        Map map = new HashMap();
        LinkedHashMap hashMap = (LinkedHashMap) obj;
        Iterator lit = hashMap.entrySet().iterator();
        while (lit.hasNext()) {
            Map.Entry e = (Map.Entry) lit.next();
            map.put(e.getKey(), e.getValue());
        }
        return map;
    }

    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public static Map idAndName(Object id, String name) {
        Map returnMap = new HashMap();
        returnMap.put("id", id);
        returnMap.put("name", name);
        return returnMap;
    }

    public static Date multipyDay(Date date, Integer day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day); //把日期往后增加一天,整数  往后推,负数往前移动
        date = new Date(calendar.getTime().getTime());

        return date;
    }
}
