package com.rs.springframework.aop.advisor;

import lombok.Data;

@Data
public class Advisor {
    private IAdvice advice;   //干什么
    private IPointcut pointcut;  //在哪里
}
