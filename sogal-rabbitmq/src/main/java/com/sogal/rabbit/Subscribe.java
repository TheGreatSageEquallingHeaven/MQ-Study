package com.sogal.rabbit;

import com.sogal.handle.MessageHandle;

import java.io.IOException;

/**
 * Created by xiaoxuwang on 2017/11/24.
 */
public interface Subscribe {

    void subscribeMessage(MessageHandle messageHandle) throws IOException;

}
