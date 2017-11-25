package com.test;

import com.sogal.config.Configuration;
import com.sogal.rabbit.impl.RabbitMQPublish;

/**
 * Created by xiaoxuwang on 2017/11/24.
 */
public class PublisherMaster {
    private static PublisherMaster ourInstance = new PublisherMaster();

    public static PublisherMaster getInstance() {
        return ourInstance;
    }

    private RabbitMQPublish rabbitMQPublish;

    private PublisherMaster() {
        Configuration config = new Configuration();
        try {
            config.init("rabbitmq.properties");
            rabbitMQPublish = RabbitMQPublish.getInstance(config);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public RabbitMQPublish getRabbitMQPublish() {
        return rabbitMQPublish;
    }
}
