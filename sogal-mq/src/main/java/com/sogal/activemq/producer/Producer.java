package com.sogal.activemq.producer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import javax.jms.*;

import com.sogal.activemq.respond.RespondMessage;
import org.springframework.jms.core.JmsTemplate;

import org.springframework.jms.core.MessageCreator;

/**
 * Created by xiaoxuwang on 2017/11/23.
 */
public class Producer implements MessageListener {

    private JmsTemplate jmsTemplate;

    private Destination requestDestination;

    private Destination replyDestination;

    private static ConcurrentMap<String, RespondMessage> concurrentMap = new ConcurrentHashMap<>();


    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Destination getRequestDestination() {
        return requestDestination;
    }

    public void setRequestDestination(Destination requestDestination) {
        this.requestDestination = requestDestination;
    }

    public Destination getReplyDestination() {
        return replyDestination;
    }

    public void setReplyDestination(Destination replyDestination) {
        this.replyDestination = replyDestination;
    }

    public String sendMessage(final String message) {
        RespondMessage replyMessage = new RespondMessage();
        final String correlationID = UUID.randomUUID().toString();
        concurrentMap.put(correlationID, replyMessage);

        jmsTemplate.send(requestDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message msg = session.createTextMessage(message);
                msg.setJMSCorrelationID(correlationID);
                msg.setJMSReplyTo(replyDestination);
                return msg;
            }
        });
        try {
            boolean isReceiveMessage = replyMessage.getSemaphore().tryAcquire(10, TimeUnit.SECONDS);

            RespondMessage result = concurrentMap.get(correlationID);

            if (isReceiveMessage && null != result) {
                Message msg = result.getMessage();
                if (null != msg) {
                    TextMessage textMessage = (TextMessage) msg;
                    return textMessage.getText();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                concurrentMap.get(textMessage.getJMSCorrelationID()).setMessage(textMessage);
                concurrentMap.get(textMessage.getJMSCorrelationID()).getSemaphore().release();
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
