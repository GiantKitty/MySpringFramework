package com.rs.springframework.aop.interceptor;

import com.rs.springframework.aop.invocation.IMethodInvocation;

/**
 * 方法的拦截器
 */
public interface IAopMethodInterceptor {
    Object invoke(IMethodInvocation methodInvocation) throws Throwable;
}
