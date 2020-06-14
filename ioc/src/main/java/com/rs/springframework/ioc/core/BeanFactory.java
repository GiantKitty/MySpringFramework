package com.rs.springframework.ioc.core;

public interface BeanFactory {
    Object getBean(String beanName) throws Exception;
}
