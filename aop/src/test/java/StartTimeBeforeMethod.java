import com.rs.springframework.aop.advisor.IBeforeMethodAdvice;

import java.lang.reflect.Method;

public class StartTimeBeforeMethod implements IBeforeMethodAdvice{
    public void before(Method method, Object[] args, Object target) {
        long startTime = System.currentTimeMillis();
        System.out.println("开始计时");
        ThreadLocalUtils.set(startTime);
    }
}