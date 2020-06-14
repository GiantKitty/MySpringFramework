import com.rs.springframework.aop.core.AopApplicationContext;

public class MainTest {
    public static void main(String[] args) throws Exception {
        AopApplicationContext aopApplictionContext = new AopApplicationContext("application.json");
        aopApplictionContext.init();
        TestService testService = (TestService) aopApplictionContext.getBean("testServiceProxy");
        testService.testMethod();
    }
}

