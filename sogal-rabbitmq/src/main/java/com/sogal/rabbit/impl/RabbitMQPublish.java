package com.sogal.rabbit.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sogal.config.Configuration;
import com.sogal.rabbit.Publish;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.sogal.constant.ConfigurationConstant.*;

/**
 * Created by xiaoxuwang on 2017/11/24.
 */
public class RabbitMQPublish implements Publish {

    Logger logger = Logger.getLogger(RabbitMQPublish.class);

    private Configuration config;
    private String exchangeName;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Channel channel = null;

    private RabbitMQPublish(Configuration config){
        this.config = config;
    }

    public static RabbitMQPublish getInstance(Configuration config)throws IOException , TimeoutException{
        RabbitMQPublish rabbitMQPublish = new RabbitMQPublish(config);
        return rabbitMQPublish.bulid();
    }

    private RabbitMQPublish bulid() throws IOException , TimeoutException{
        factory = new ConnectionFactory();
        factory.setHost(config.getString(RABBITMQ_HOST));
        factory.setPort(Integer.parseInt(config.getString(RABBITMQ_PORT)));
        factory.setUsername(config.getString(RABBITMQ_USERNAME));
        factory.setPassword(config.getString(RABBITMQ_PASSWORD));
        connection = factory.newConnection();
        return this;
    }

    @Override
    public void publishMessage(String message) throws IOException,TimeoutException {
        logger.info("send message -->" + message);
        exchangeName = config.getString(RABBITMQ_EXCHANGE_NAME);
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName,RABBITMQ_EXCHANGE_TYPE,true);
        channel.basicPublish(exchangeName,"",null,message.getBytes());
        channel.close();
    }

    public void close() throws IOException,TimeoutException  {
        if(null != connection) {
            connection.close();
        }
    }
}
