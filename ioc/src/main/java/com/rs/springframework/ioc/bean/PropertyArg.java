package com.rs.springframework.ioc.bean;


import lombok.Data;
import lombok.ToString;

/**
 * 需要注入的参数
 */
@Data
@ToString
public class PropertyArg {

    private String propertyName;
    private String propertyValue;
    private String propertyTypeName;
    private String propertyRef;

}
