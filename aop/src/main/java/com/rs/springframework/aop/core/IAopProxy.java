package com.rs.springframework.aop.core;

public interface IAopProxy {
    Object getProxy();
    Object getProxy(ClassLoader classLoader);
}
