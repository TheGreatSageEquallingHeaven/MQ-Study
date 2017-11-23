package com.sogal.activemq.context;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiaoxuwang on 2017/11/22.
 */
public enum ActiveAppContextManager {

    INSTANCE;

    private AbstractApplicationContext activeContext = null;

    ActiveAppContextManager(){
        activeContext = new ClassPathXmlApplicationContext("spring/spring-activemq.xml");
    }

    public AbstractApplicationContext buildActiveMQContext(){
        return activeContext;
    }


}
