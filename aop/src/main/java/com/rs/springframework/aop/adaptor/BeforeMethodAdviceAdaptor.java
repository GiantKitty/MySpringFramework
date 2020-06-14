package com.rs.springframework.aop.adaptor;

import com.rs.springframework.aop.advisor.Advisor;
import com.rs.springframework.aop.advisor.IBeforeMethodAdvice;
import com.rs.springframework.aop.interceptor.BeforeMethodAdviceInterceptor;
import com.rs.springframework.aop.interceptor.IAopMethodInterceptor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BeforeMethodAdviceAdaptor implements IAdviceAdaptor{

    //单例
    private static final BeforeMethodAdviceAdaptor INSTANCE = new BeforeMethodAdviceAdaptor();
    public static BeforeMethodAdviceAdaptor getInstance(){
        return INSTANCE;
    }

    public IAopMethodInterceptor getInterceptor(Advisor advisor) {
        IBeforeMethodAdvice advice = (IBeforeMethodAdvice) advisor.getAdvice();
        return new BeforeMethodAdviceInterceptor(advice);
    }
}
