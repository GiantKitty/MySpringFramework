package com.rs.springframework.aop.advisor;

import com.rs.springframework.aop.interceptor.IAopMethodInterceptor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 这个AdvisedSupport就是 我们Aop框架能够理解的数据结构，这个时候问题就变成了–对于哪个目标，增加哪些拦截器。
 */
@Data
public class AdvisedSupport extends Advisor{

    private TargetSource targetSource; //目标对象
    private List<IAopMethodInterceptor> interceptorList = new LinkedList();//拦截器列表

    public void addAopMethodInterceptor(IAopMethodInterceptor interceptor){
        interceptorList.add(interceptor);
    }

    // 批量添加
    public void addAopMethodInterceptor(List<IAopMethodInterceptor> interceptors){
        interceptorList.addAll(interceptors);
    }

}
