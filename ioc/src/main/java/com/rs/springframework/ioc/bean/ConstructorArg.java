package com.rs.springframework.ioc.bean;

import lombok.Data;
import lombok.ToString;

/**
 * 构造函数的传参的列表
 */
@Data
@ToString
public class ConstructorArg {
    private int constructorArgIndex;
    private String constructorArgRef;
    private String constructorArgName;
    private Object constructorArgValue;
}