package com.rs.springframework.aop.bean;

import com.rs.springframework.aop.adaptor.AfterRunningAdviceAdaptor;
import com.rs.springframework.aop.adaptor.BeforeMethodAdviceAdaptor;
import com.rs.springframework.aop.advisor.*;
import com.rs.springframework.aop.core.CglibAopProxy;
import com.rs.springframework.aop.interceptor.AfterRunningAdviceInterceptor;
import com.rs.springframework.aop.interceptor.IAopMethodInterceptor;
import com.rs.springframework.ioc.core.BeanFactoryImpl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 产生代理对象的工厂类
 */
public class AopBeanFactoryImpl extends BeanFactoryImpl {
    private static final ConcurrentHashMap<String,AopBeanDefinition> aopBeanDefinitionMap = new ConcurrentHashMap();
    private static final ConcurrentHashMap<String,Object> aopBeanMap = new ConcurrentHashMap();

    /**
     * 注册bean
     */
    protected void registerBean(String beanName, AopBeanDefinition aopBeanDefinition){
        aopBeanDefinitionMap.put(beanName,aopBeanDefinition);
    }

    /**重写 getBean方法
     * 如果是一个切面代理类，我们使用Aop框架生成代理类
     * 如果是普通的对象，我们就用原来的IoC容器进行依赖注入
     */
    public Object getBean(String beanName) throws Exception {
        Object aopBean = aopBeanMap.get(beanName);
        if(aopBean != null){
            return aopBean;
        }
        if(aopBeanDefinitionMap.containsKey(beanName)){ //如果是一个切面代理类
            AopBeanDefinition aopBeanDefinition = aopBeanDefinitionMap.get(beanName);
            AdvisedSupport advisedSupport = getAdvisedSupport(aopBeanDefinition);
            aopBean = new CglibAopProxy(advisedSupport).getProxy(); //使用Aop框架生成代理类
            aopBeanMap.put(beanName,aopBean);
            return aopBean;
        }
        return super.getBean(beanName); //如果是普通的对象，我们就用原来的IoC容器进行依赖注入。
    }

    /**
     * 获取 Aop 框架认识的数据结构
     */
    private AdvisedSupport getAdvisedSupport(AopBeanDefinition aopBeanDefinition) throws Exception {
        AdvisedSupport advisedSupport = new AdvisedSupport();
        List<String> interceptorNames = aopBeanDefinition.getInterceptorNames();
        if(interceptorNames != null && !interceptorNames.isEmpty()){
            for(String interceptorName : interceptorNames){
                IAdvice advice = (IAdvice) getBean(interceptorName);
                Advisor advisor = new Advisor();
                advisor.setAdvice(advice);

                IAopMethodInterceptor interceptor = null;
                if(advice instanceof IBeforeMethodAdvice){
                    interceptor = (IAopMethodInterceptor) BeforeMethodAdviceAdaptor.getInstance();
                }
                if (advice instanceof IAfterRunningAdvice){
                    interceptor = (IAopMethodInterceptor) AfterRunningAdviceAdaptor.getInstance();
                }
                advisedSupport.addAopMethodInterceptor(interceptor);

            }
        }

        TargetSource targetSource = new TargetSource();
        Object object = getBean(aopBeanDefinition.getTarget());
        targetSource.setTargetClass(object.getClass());
        targetSource.setTargetObject(object);
        advisedSupport.setTargetSource(targetSource);
        return advisedSupport;

    }




}
