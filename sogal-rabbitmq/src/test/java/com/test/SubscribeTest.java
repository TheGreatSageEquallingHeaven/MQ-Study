package com.test;

import com.sogal.config.Configuration;
import com.sogal.handle.MessageHandle;
import com.sogal.rabbit.Subscribe;
import com.sogal.rabbit.impl.RabbitMQSubscribe;

/**
 * Created by xiaoxuwang on 2017/11/24.
 */
public class SubscribeTest {
    public static  void main(String []arges){
        Configuration config = new Configuration();
        Subscribe subscribe = null;

        try {
            config.init("rabbitmq.properties");
            subscribe = RabbitMQSubscribe.getInstance(config);
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            MessageHandle messageHandle = new MessageHandleRule();
            subscribe.subscribeMessage(messageHandle);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
