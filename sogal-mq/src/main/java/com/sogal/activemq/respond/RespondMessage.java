package com.sogal.activemq.respond;

import javax.jms.Message;
import java.util.concurrent.Semaphore;

/**
 * Created by xiaoxuwang on 2017/11/23.
 */
public class RespondMessage {

    private Semaphore semaphore = new Semaphore(0);

    private Message message;

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
