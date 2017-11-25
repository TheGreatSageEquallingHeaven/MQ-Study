package com.test;

import com.sogal.handle.MessageHandle;

/**
 * Created by xiaoxuwang on 2017/11/24.
 */
public class MessageHandleRule implements MessageHandle {

    @Override
    public void doCheck(String Message) {
        System.out.println(Message);
    }
}
