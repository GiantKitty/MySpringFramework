package com.rs.springframework.ioc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 主要通过 Java 的反射原理来完成对象的依赖注入
 */
public class ReflectionUtils {
    // 为bean对象的属性注入依赖值
    public static void injectField(Field field, Object obj, Object value) throws IllegalAccessException {
        if(field != null){
            field.setAccessible(true);
            field.set(obj,value); // obj.field=value;
        }
    }

}
