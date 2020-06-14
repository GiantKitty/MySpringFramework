package com.rs.springframework.aop.invocation;

/**
 * 代理方法的调用
 */
public interface IProxyMethodInvocation extends IMethodInvocation{
    Object getProxy();
}
