import com.sogal.activemq.context.ActiveAppContextManager;
import com.sogal.activemq.producer.Producer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created by xiaoxuwang on 2017/11/23.
 */
public class ActiveMQTest {

    private Producer producer = null;
    private AbstractApplicationContext abstractApplicationContext = null;

    @Before
    public void setUp() throws Exception{
        abstractApplicationContext = ActiveAppContextManager.INSTANCE.buildActiveMQContext();
        producer = abstractApplicationContext.getBean(Producer.class);
    }

    @Test
    public void testProducer() {
        String request = "线程1-"+ Thread.currentThread().getName();
        producer.sendMessage(request);
    }

}
