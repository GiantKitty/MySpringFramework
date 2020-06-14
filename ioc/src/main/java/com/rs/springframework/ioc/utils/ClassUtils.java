package com.rs.springframework.ioc.utils;

/**
 * 负责处理 Java 类的加载
 */
public class ClassUtils {
    public static ClassLoader getDefaultClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    //通过 className 这个参数获取对象的 Class
    public static Class loadClass(String className){
        Class clazz = null;
        try {
            clazz = getDefaultClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }
}
