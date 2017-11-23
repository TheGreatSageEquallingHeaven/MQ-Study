package com.sogal.rabbitmq.sender;

import com.sogal.rabbitmq.context.AppContextManager;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created by xiaoxuwang on 2017/11/22.
 */
public class RabbitMessageSender {

    private AbstractApplicationContext ctx  = AppContextManager.INSTANCE.buildRabbitMQContext();

    public void send(String message) {
        AmqpTemplate amqpTemplate = (AmqpTemplate)ctx.getBean("amqpTemplate");
        amqpTemplate.convertAndSend(message);
    }
}
