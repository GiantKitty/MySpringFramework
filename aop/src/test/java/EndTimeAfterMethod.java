import com.rs.springframework.aop.advisor.IAfterRunningAdvice;

import java.lang.reflect.Method;

public class EndTimeAfterMethod implements IAfterRunningAdvice {
    public Object after(Object returnVal, Method method, Object[] args, Object target) {
        long endTime = System.currentTimeMillis();
        long startTime = ThreadLocalUtils.get();
        ThreadLocalUtils.remove();
        System.out.println("方法耗时：" + (endTime - startTime) + "ms");
        return returnVal;
    }
}