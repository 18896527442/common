package com.ll.mq.rabbitmq.demo;


import com.ll.mq.rabbitmq.client.RabbitMqClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private RabbitMqClient rabbitMqClient;

    public void sendMessege(){
        User user = new User();
        user.setId(1);
        user.setAge(22);
        user.setName("ll");

        rabbitMqClient.sendMessage(QueueConstant.USER_QUEUE,user);
    }
}
