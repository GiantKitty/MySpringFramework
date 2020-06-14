import com.rs.springframework.ioc.core.JsonApplicationContext;
import com.rs.springframework.ioc.entity.Robot;

public class Test {

    public static void main(String[] args) throws Exception {
        JsonApplicationContext ac = new JsonApplicationContext("application.json");
        ac.init();

        Robot robot = (Robot) ac.getBean("robot");
        robot.show();

    }


}
