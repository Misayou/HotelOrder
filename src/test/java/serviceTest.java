import com.web.core.service.OrderSerive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Auther: http://www.tanwei.com
 * @Date: 2019/7/28
 * @Description: PACKAGE_NAME
 * @versio: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-service.xml","classpath:applicationContext-dao.xml"})
public class serviceTest {
    @Autowired
    private OrderSerive orderSerive;

    @Test
    public void setOrderSeriveTest(){
        String str = orderSerive.getUserOrder(15);
        System.out.println(str);
    }
}
