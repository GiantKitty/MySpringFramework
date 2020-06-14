package com.rs.springframework.aop.invocation;

import java.lang.reflect.Method;

/**
 * 用于描述方法的调用
 */
public interface IMethodInvocation {
    Method getMethod();
    Object[] getArguments();
    Object proceed() throws Throwable;
}
