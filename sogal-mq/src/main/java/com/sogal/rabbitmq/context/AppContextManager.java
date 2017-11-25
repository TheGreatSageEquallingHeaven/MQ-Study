package com.sogal.rabbitmq.context;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiaoxuwang on 2017/11/22.
 */
public enum AppContextManager {

    INSTANCE;

    private AbstractApplicationContext rabbitContext = null;

    AppContextManager(){
        rabbitContext = new ClassPathXmlApplicationContext("spring/spring-rabbitmq.xml");
    }

    public AbstractApplicationContext buildRabbitMQContext(){
        return rabbitContext;
    }


}
