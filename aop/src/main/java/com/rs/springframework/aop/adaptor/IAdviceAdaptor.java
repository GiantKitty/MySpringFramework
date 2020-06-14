package com.rs.springframework.aop.adaptor;

import com.rs.springframework.aop.advisor.Advisor;
import com.rs.springframework.aop.interceptor.IAopMethodInterceptor;

public interface IAdviceAdaptor {
    IAopMethodInterceptor getInterceptor(Advisor advisor);
}
