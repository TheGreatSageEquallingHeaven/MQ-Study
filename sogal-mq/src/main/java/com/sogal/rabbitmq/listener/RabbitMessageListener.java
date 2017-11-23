package com.sogal.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Created by xiaoxuwang on 2017/11/22.
 */
@Component
public class RabbitMessageListener implements MessageListener{

    @Override
    public void onMessage(Message message){
        System.out.println("Get Message [" + new String(message.getBody()) + "]");
    }

}
