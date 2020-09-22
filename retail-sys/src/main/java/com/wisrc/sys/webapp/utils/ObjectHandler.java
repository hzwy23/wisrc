package com.wisrc.sys.webapp.utils;

import java.lang.reflect.Field;
import java.util.*;

public class ObjectHandler {
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }

    public static List objectToList(Object obj) throws Exception {
        List list = new ArrayList();
        if (obj instanceof ArrayList) {
            list = (ArrayList) obj;
        }
        return list;
    }

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
}
