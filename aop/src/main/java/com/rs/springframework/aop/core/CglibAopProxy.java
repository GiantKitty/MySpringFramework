package com.rs.springframework.aop.core;

import com.rs.springframework.aop.advisor.AdvisedSupport;
import com.rs.springframework.aop.interceptor.DynamicAdvisedInterceptor;
import com.rs.springframework.ioc.utils.ClassUtils;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

/**
 * 生成代理对象
 */
public class CglibAopProxy implements IAopProxy{

    private AdvisedSupport advised;
    private Object[] constructorArgs;
    private Class<?>[] constructorArgTypes;

    public CglibAopProxy(AdvisedSupport config){
        this.advised = config;
    }


    public Object getProxy() {
        return getProxy(null);
    }

    public Object getProxy(ClassLoader classLoader) {
        Class<?> rootClass = advised.getTargetSource().getTargetClass();
        if(classLoader == null){
            classLoader = ClassUtils.getDefaultClassLoader();
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(rootClass.getSuperclass());
        //增加拦截器的核心方法
        Callback callbacks = getCallBack(advised);
        enhancer.setCallback(callbacks);
        enhancer.setClassLoader(classLoader);
        //有参
        if(constructorArgs != null && constructorArgs.length > 0){
            return enhancer.create(constructorArgTypes,constructorArgs);
        }
        return enhancer.create();  // 无参

    }

    private Callback getCallBack(AdvisedSupport advised) {
        return new DynamicAdvisedInterceptor(advised.getInterceptorList(),advised.getTargetSource());
    }
}
