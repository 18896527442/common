package com.ll.mq.rabbitmq.demo;


import com.ll.mq.rabbitmq.annotation.RabbitComponent;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

@Slf4j
@RabbitComponent("userRabbitMqListener")
public class UserRabbitMqListener {

    @RabbitListener(queues = {QueueConstant.USER_QUEUE})
    public void handlerUserMessege(Message message, Channel channel, User user){
        log.info("接收到mq消息"+message);
        log.info("user="+ user.toString());
        try {
            //手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
