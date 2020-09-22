package com.wisrc.purchase.webapp.utils;

import org.springframework.cglib.beans.BeanMap;

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

    public static List distinctList(List list) {
        Set set = new HashSet();
        for (Object obj : list) {
            set.add(obj);
        }
        return new ArrayList<>(set);
    }
}
