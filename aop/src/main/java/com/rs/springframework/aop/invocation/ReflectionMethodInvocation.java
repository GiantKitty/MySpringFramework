package com.rs.springframework.aop.invocation;

import com.rs.springframework.aop.interceptor.IAopMethodInterceptor;
import com.rs.springframework.aop.utils.ReflectionUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;


public class ReflectionMethodInvocation implements IProxyMethodInvocation{

    protected final Object proxy;  // 代理对象
    protected final Object target; //目标对象
    protected final Method method; //目标对象原本的方法
    protected Object[] arguments = new Object[0];  //方法的参数
    protected final List<IAopMethodInterceptor> interceptorList; // 拦截器列表
    private int currentInterceptorIndex = -1;  // 拦截器链中的索引

    public ReflectionMethodInvocation(Object proxy, Object target, Method method, Object[] arguments,
                                      List<IAopMethodInterceptor> interceptorList) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.interceptorList = interceptorList;
    }

    public Object getProxy() {
        return null;
    }

    public Method getMethod() {
        return null;
    }

    public Object[] getArguments() {
        return new Object[0];
    }

    public Object proceed() throws Throwable {
        //执行完所有的拦截器后，才会真正调用被增强的方法(目标方法)。
        if(currentInterceptorIndex == this.interceptorList.size()-1){
            return invokeOriginal();
        }
        //执行拦截器。我们实现的拦击都会执行
        //im.proceed() 实际上就在调用这个方法。
        IAopMethodInterceptor interceptor = interceptorList.get(++currentInterceptorIndex);
        return interceptor.invoke(this);
    }

    protected Object invokeOriginal() throws Throwable{
        return ReflectionUtils.invokeMethodUseReflection(target,method,arguments);
    }


}
