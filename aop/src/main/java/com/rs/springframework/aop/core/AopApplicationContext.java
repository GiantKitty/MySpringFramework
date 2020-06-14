package com.rs.springframework.aop.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.rs.springframework.aop.bean.AopBeanDefinition;
import com.rs.springframework.aop.bean.AopBeanFactoryImpl;
import com.rs.springframework.aop.bean.ProxyFactoryBean;
import com.rs.springframework.ioc.bean.BeanDefinition;
import com.rs.springframework.ioc.utils.ClassUtils;
import com.rs.springframework.ioc.utils.JsonUtils;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.util.List;

@AllArgsConstructor
public class AopApplicationContext  extends AopBeanFactoryImpl {
    private String fileName;

    public void init(){
        loadFile();
    }

    private void loadFile(){

        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

        List<AopBeanDefinition> beanDefinitions = JsonUtils.readValue(is,new TypeReference<List<AopBeanDefinition>>(){});

        if(beanDefinitions != null && !beanDefinitions.isEmpty()) {

            for (AopBeanDefinition beanDefinition : beanDefinitions){
                Class<?> clz = ClassUtils.loadClass(beanDefinition.getBeanClassName());
                if(clz == ProxyFactoryBean.class){
                    registerBean(beanDefinition.getBeanName(),beanDefinition);
                }else {
                    registerBean(beanDefinition.getBeanName(),(BeanDefinition) beanDefinition);
                }
            }
        }

    }
}
