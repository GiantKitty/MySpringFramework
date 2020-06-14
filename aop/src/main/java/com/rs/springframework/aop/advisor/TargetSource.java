package com.rs.springframework.aop.advisor;

import lombok.Data;

@Data
public class TargetSource {
    private Class<?> targetClass;
    private Object targetObject;
}
