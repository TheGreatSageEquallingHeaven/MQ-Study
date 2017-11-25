package com.sogal.rabbit.impl;

import com.rabbitmq.client.*;
import com.sogal.config.Configuration;
import com.sogal.handle.MessageHandle;
import com.sogal.rabbit.Subscribe;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.sogal.constant.ConfigurationConstant.*;
import static com.sogal.constant.ConfigurationConstant.RABBITMQ_PASSWORD;

/**
 * Created by xiaoxuwang on 2017/11/24.
 */
public class RabbitMQSubscribe implements Subscribe {

    Logger logger = Logger.getLogger(RabbitMQSubscribe.class);

    private Configuration config;
    private String exchangeName;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Channel channel = null;
    private String queueName;

    private RabbitMQSubscribe(Configuration config){
        this.config = config;
    }

    public static RabbitMQSubscribe getInstance(Configuration config)throws IOException,
            TimeoutException {
        RabbitMQSubscribe rabbitMQSubscribe = new RabbitMQSubscribe(config).bulid();
        return rabbitMQSubscribe;
    }


    private RabbitMQSubscribe bulid() throws IOException, TimeoutException {
        exchangeName = config.getString(RABBITMQ_EXCHANGE_NAME);
        factory = new ConnectionFactory();
        factory.setPort(Integer.parseInt(config.getString(RABBITMQ_PORT)));
        factory.setHost(config.getString(RABBITMQ_HOST));
        factory.setUsername(config.getString(RABBITMQ_USERNAME));
        factory.setPassword(config.getString(RABBITMQ_PASSWORD));
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName,RABBITMQ_EXCHANGE_TYPE,true);
        queueName  = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, "");
        return this;

    }

    @Override
    public void subscribeMessage(MessageHandle messageHandle) throws IOException {
        final Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                logger.info("receive message -->" + message);
                messageHandle.doCheck(message);

            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    public void close() throws IOException,TimeoutException  {
        if(null != connection) {
            connection.close();
        }
    }
}
