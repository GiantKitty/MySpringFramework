package com.rs.springframework.aop.advisor;

import java.lang.reflect.Method;

public interface IBeforeMethodAdvice extends IAdvice{
    void before(Method method, Object[] args, Object target);
}
