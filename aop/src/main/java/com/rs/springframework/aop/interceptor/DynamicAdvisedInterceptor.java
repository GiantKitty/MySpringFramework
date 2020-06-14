package com.rs.springframework.aop.interceptor;

import com.rs.springframework.aop.advisor.TargetSource;
import com.rs.springframework.aop.invocation.CglibMethodInvocation;
import com.rs.springframework.aop.invocation.IMethodInvocation;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class DynamicAdvisedInterceptor implements MethodInterceptor {

    protected final List<IAopMethodInterceptor> interceptorList;
    protected final TargetSource targetSource;

    public DynamicAdvisedInterceptor(List<IAopMethodInterceptor> interceptorList, TargetSource targetSource) {
        this.interceptorList = interceptorList;
        this.targetSource = targetSource;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //这个动态的拦截器，把我们通过 CglibMethodInvocation 织入了增强代码的方法，委托给了 cglib 来生成代理对象。
        IMethodInvocation invocation = new CglibMethodInvocation(obj,targetSource.getTargetObject(),method,args,interceptorList,methodProxy);
        return invocation.proceed();
    }
}
