package com.rs.springframework.aop.interceptor;

import com.rs.springframework.aop.advisor.IBeforeMethodAdvice;
import com.rs.springframework.aop.invocation.IMethodInvocation;

public class BeforeMethodAdviceInterceptor implements IAopMethodInterceptor{



    private IBeforeMethodAdvice advice;

    public BeforeMethodAdviceInterceptor(IBeforeMethodAdvice advice) {
        this.advice = advice;
    }

    public Object invoke(IMethodInvocation methodInvocation) throws Throwable {
        advice.before(methodInvocation.getMethod(),methodInvocation.getArguments(),methodInvocation);
        return methodInvocation.proceed();  //执行原有的方法
    }
}
