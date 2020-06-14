package com.rs.springframework.aop.bean;

import com.rs.springframework.ioc.bean.BeanDefinition;
import lombok.Data;

import java.util.List;

@Data
public class AopBeanDefinition extends BeanDefinition {
    private String target;
    private List<String> interceptorNames;
}
