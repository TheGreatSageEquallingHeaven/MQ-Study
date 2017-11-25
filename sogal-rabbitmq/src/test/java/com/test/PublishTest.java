package com.test;

/**
 * Created by xiaoxuwang on 2017/11/24.
 */
public class PublishTest {

    public static void main(String []args) {
        for(int i=0;i<=20;i++) {
            try {
                PublisherMaster.getInstance().getRabbitMQPublish().publishMessage("acb" + i);
            }catch (Exception e){
                e.printStackTrace();

            }
        }
        try {
            PublisherMaster.getInstance().getRabbitMQPublish().close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
