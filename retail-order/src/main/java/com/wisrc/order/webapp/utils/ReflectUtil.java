package com.wisrc.order.webapp.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectUtil {
    public static Map<String, Object> compareAttributeValue(Object obj, Object obj1) {
        //得到class
        Class cls = obj.getClass();
        Class cls1 = obj1.getClass();
        Map<String, Object> map = new HashMap<>();
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        Field[] fields1 = cls1.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {//遍历
            try {
                //得到属性
                Field field = fields[i];
                //打开私有访问
                field.setAccessible(true);

                Field field1 = fields1[i];
                //打开私有访问
                field1.setAccessible(true);
                //获取属性
                String name = field.getName();
                //获取属性值
                Object value = field.get(obj);
                String valueStr = value + "";
                String name1 = field1.getName();
                //获取属性值
                Object value1 = field1.get(obj1);
                String valueStr1 = value1 + "";
                if (!valueStr.equals(valueStr1)) {
                    Map<String, String> changeMap = new HashMap();
                    changeMap.put("oldValue", valueStr);
                    changeMap.put("newValue", valueStr1);
                    map.put(name, changeMap);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
