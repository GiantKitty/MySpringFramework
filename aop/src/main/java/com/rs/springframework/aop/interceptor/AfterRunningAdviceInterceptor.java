package com.rs.springframework.aop.interceptor;

import com.rs.springframework.aop.advisor.IAfterRunningAdvice;
import com.rs.springframework.aop.invocation.IMethodInvocation;

public class AfterRunningAdviceInterceptor implements IAopMethodInterceptor{
    private IAfterRunningAdvice advice;

    public AfterRunningAdviceInterceptor(IAfterRunningAdvice advice) {
        this.advice = advice;
    }

    public Object invoke(IMethodInvocation methodInvocation) throws Throwable {
        Object returnVal = methodInvocation.proceed();  //执行原有的方法
        advice.after(returnVal,methodInvocation.getMethod(),methodInvocation.getArguments(),methodInvocation);
        return returnVal;
    }
}
