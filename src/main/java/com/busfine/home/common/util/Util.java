package com.busfine.home.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Util {
    public static Map<String, Object> AllFields(Object obj) {
        Map<String, Object> fieldMap = new HashMap<>();
        if (obj == null){
            return null;
        }
        Class<?> clazz = obj.getClass();

        // 부모 클래스까지 올라가면서 필드 출력
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field item : fields) {
                item.setAccessible(true); // private 필드 접근 허용
                try {
                    Object value = item.get(obj);
                    fieldMap.put(item.getName(), value);

                } catch (IllegalAccessException e) {
                    fieldMap.put(item.getName(), "접근 불가");
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fieldMap;
    }
}
