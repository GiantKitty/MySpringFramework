package com.rs.springframework.aop.adaptor;

import com.rs.springframework.aop.advisor.Advisor;
import com.rs.springframework.aop.advisor.IAfterRunningAdvice;
import com.rs.springframework.aop.interceptor.AfterRunningAdviceInterceptor;
import com.rs.springframework.aop.interceptor.IAopMethodInterceptor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AfterRunningAdviceAdaptor implements IAdviceAdaptor{

    private static final AfterRunningAdviceAdaptor INSTANCE = new AfterRunningAdviceAdaptor();

    public static AfterRunningAdviceAdaptor getInstance(){
        return INSTANCE;
    }

    public IAopMethodInterceptor getInterceptor(Advisor advisor) {
        IAfterRunningAdvice advice = (IAfterRunningAdvice) advisor.getAdvice();
        return new AfterRunningAdviceInterceptor(advice);
    }
}
