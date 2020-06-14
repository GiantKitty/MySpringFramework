package com.rs.springframework.aop.invocation;

import com.rs.springframework.aop.interceptor.IAopMethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class CglibMethodInvocation extends ReflectionMethodInvocation{

    private MethodProxy methodProxy;

    public CglibMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
                                 List<IAopMethodInterceptor> interceptorList, MethodProxy methodProxy) {
        super(proxy, target, method, arguments, interceptorList);
        this.methodProxy = methodProxy;
    }

    //重写了 invokeOriginal 方法。使用cglib的代理类来调用被增强的方法。
    protected Object invokeOriginal() throws Throwable {
        return methodProxy.invoke(target,arguments);
    }

}
