package com.rs.springframework.ioc.core;

import com.rs.springframework.ioc.bean.BeanDefinition;
import com.rs.springframework.ioc.bean.ConstructorArg;
import com.rs.springframework.ioc.bean.PropertyArg;
import com.rs.springframework.ioc.utils.BeanUtils;
import com.rs.springframework.ioc.utils.ClassUtils;
import com.rs.springframework.ioc.utils.ReflectionUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactoryImpl implements BeanFactory{

    // beanMap 用于保存已经实例化好的对象.
    private static final ConcurrentHashMap<String,Object> beanMap = new ConcurrentHashMap<String,Object>();
    // beanDefineMap存储的是对象的名称和对象对应的数据结构的映射
    private static final ConcurrentHashMap<String, BeanDefinition> beanDefineMap = new ConcurrentHashMap<String, BeanDefinition>();
    private static final Set<String> beanNameSet = Collections.synchronizedSet(new HashSet<String>());
    // 通过 earlySingletonObjects 解决循环依赖
    private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);

    // 向容器中注册bean，不是注册的bean本身，是bean的BeanDefinition
    protected void registerBean(String beanName, BeanDefinition bd){
        beanDefineMap.put(beanName,bd);
        beanNameSet.add(beanName);
    }

    public Object getBean(String beanName) throws Exception {
        Object bean = beanMap.get(beanName);
        if(bean != null){
            return bean;
        }
        //bean不存在时，不是去create他，而是去早期单例中拿
        Object earlyBean = earlySingletonObjects.get(beanName);

        if(earlyBean != null){
            System.out.println("发生了循环依赖，提前返回尚未加载完成的bean:" + beanName);
            return earlyBean;
        }
        //如果没有实例化，缓存中也没有这个bean的话，才需要调用createBean来创建对象
        BeanDefinition beanDefinition = beanDefineMap.get(beanName);
        bean = createBean(beanDefinition);
        if (bean != null) {
            //为了解决循环依赖，先添加到早期单例中
            earlySingletonObjects.put(beanName, bean);  // start
            //对象创建成功以后，注入对象需要的参数
            populatebean(bean,beanDefinition);
            //再把对象存入Map中方便下次使用。
            beanMap.put(beanName, bean);
            //从早期单例 Map 中移除
            earlySingletonObjects.remove(beanName);  //end
        }
        return bean;
    }

    //注入相应的依赖。obj.field=value;
    private void populatebean(Object bean, BeanDefinition bd) throws Exception {
        List<PropertyArg> propertyArgs = bd.getBeanPropertyArgs();
        if(propertyArgs!=null && !propertyArgs.isEmpty()){
            //遍历在json中配置的属性
            for(PropertyArg arg:propertyArgs){
                String propertyName = arg.getPropertyName();
                String value = arg.getPropertyValue();
                String ref = arg.getPropertyRef();

                Object injectValue = null;
                if(value!=null){ //直接赋值
                    injectValue = value;
                }else if(ref!=null && !"".equals(ref)){
                    injectValue = getBean(ref);  //获取bean
                }
                Method setterMethod = getPropertySetter(bd,propertyName,injectValue);
                setterMethod.invoke(bean,injectValue);
            }
        }
    }

    private Object createBean(BeanDefinition beanDefinition) throws Exception {
        Class clazz = ClassUtils.loadClass(beanDefinition.getBeanClassName());
        if(clazz == null){
            throw new Exception("can not find bean by beanName");
        }

        List<ConstructorArg> constructorArgs = beanDefinition.getBeanConstructorArgs();
        // 有参构造器
        if(constructorArgs!=null && !constructorArgs.isEmpty()){
            List<Object> objects = new ArrayList<Object>();
            for(ConstructorArg arg:constructorArgs){
                objects.add(getBean(arg.getConstructorArgRef()));
            }
            return BeanUtils.instanceByCglib(clazz,clazz.getConstructor(),objects.toArray());
        }else{
            return BeanUtils.instanceByCglib(clazz,null,null);
        }

    }
    /**
     * 获取具体某个属性的setter方法
     * 此处做法比较简单粗暴
     * 实际上Spring在读取配置文件时就已经将各属性，方法，getter/setter都读取好了。
     * 在这就只需要调用BeanWrapper的方法来为属性赋值就可以了。
     * bd所代表的class对象是obj的话，那么这几个参数之间的关系是：obj.propertyName.set(injectValue);
     */
    private Method getPropertySetter(BeanDefinition bd, String propertyName, Object injectValue) throws Exception {
        Class beanClass = Class.forName(bd.getBeanClassName());
        Class injectClass = injectValue.getClass();
        Class injectSuperClass = injectClass.getSuperclass();

        if(injectSuperClass!=null && injectSuperClass!=Object.class){ //目的是啥？
            injectClass = injectSuperClass;
        }
        propertyName = propertyName.substring(0,1).toUpperCase()+propertyName.substring(1);
        Method setter = beanClass.getMethod("set"+propertyName,injectClass);
        return setter;
    }


}
