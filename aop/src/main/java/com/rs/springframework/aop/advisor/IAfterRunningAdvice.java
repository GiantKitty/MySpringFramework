package com.rs.springframework.aop.advisor;

import java.lang.reflect.Method;

public interface IAfterRunningAdvice extends IAdvice{
    Object after(Object returnVal, Method method, Object[] args, Object target);
}
