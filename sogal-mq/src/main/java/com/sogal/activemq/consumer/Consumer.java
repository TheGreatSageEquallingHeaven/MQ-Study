package com.sogal.activemq.consumer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by xiaoxuwang on 2017/11/23.
 */
public class Consumer implements MessageListener{

    private JmsTemplate jmsTemplate;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                final String request = textMessage.getText();
                System.out.println(request);
                Destination destination = textMessage.getJMSReplyTo();
                final String jmsCorrelationID = textMessage.getJMSCorrelationID();
                jmsTemplate.send(destination, new MessageCreator(){
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        Message msg = session.createTextMessage(request + "的应答！");
                        msg.setJMSCorrelationID(jmsCorrelationID);
                        return msg;
                    }
                });
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
