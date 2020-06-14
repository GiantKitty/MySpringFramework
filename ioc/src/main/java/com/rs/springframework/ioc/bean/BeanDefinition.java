package com.rs.springframework.ioc.bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * BeanDefinition 是spring的核心数据结构。
 * 用于描述我们需要 IoC 框架管理的bean对象，所需要的一些属性。
 */
@Data
@ToString
public class BeanDefinition {
    private String beanName;
    private String beanClassName;
    private String beanInterfaceName;
    private List<ConstructorArg> beanConstructorArgs;
    private List<PropertyArg> beanPropertyArgs;

}
