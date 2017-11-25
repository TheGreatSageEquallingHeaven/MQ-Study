package com.sogal.rabbit;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by xiaoxuwang on 2017/11/24.
 */
public interface Publish {

    void publishMessage(String message) throws IOException,TimeoutException;

}
