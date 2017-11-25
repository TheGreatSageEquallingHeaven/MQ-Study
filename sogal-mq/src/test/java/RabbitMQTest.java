import com.sogal.rabbitmq.context.AppContextManager;
import com.sogal.rabbitmq.sender.RabbitMessageSender;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by xiaoxuwang on 2017/11/23.
 */
public class RabbitMQTest {

<<<<<<< HEAD
//    @Before
//    public void setUp() throws Exception{
//    }

//    @Test
//    public void testRabbit() {
//        RabbitMessageSender rabbitMessageSender = new RabbitMessageSender();
//        for(int i=1;i<=10;i++){
//            rabbitMessageSender.send("xyz"+i);
//        }
//    }

//    @Test
//    public void testRabbitListener() {
////        while(true){
////            AppContextManager.INSTANCE.buildRabbitMQContext();
////        }
//    }
=======
    @Before
    public void setUp() throws Exception{
    }

    @Test
    public void testRabbit() {
        RabbitMessageSender rabbitMessageSender = new RabbitMessageSender();
        for(int i=1;i<=10;i++){
            rabbitMessageSender.send("xyz"+i);
        }
    }

    @Test
    public void testRabbitListener() {
//        while(true){
//            AppContextManager.INSTANCE.buildRabbitMQContext();
//        }
    }
>>>>>>> 9929f2e7ed2783a0d035515017380029c6e0c20b
}
