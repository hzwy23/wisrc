package com.wisrc.shipment.webapp.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageData {
    public static LinkedHashMap pack(long total, int pages, Map<String, Object> map) {
        LinkedHashMap result = new LinkedHashMap();
        result.put("total", total);
        result.put("pages", pages);
        for (String key : map.keySet()) {
            result.put(key, map.get(key));
        }
        return result;
    }

    public static LinkedHashMap pack(long total, int pages, String key, Object dat) {
        LinkedHashMap result = new LinkedHashMap();
        result.put("total", total);
        result.put("pages", pages);
        result.put(key, dat);
        return result;
    }
}
