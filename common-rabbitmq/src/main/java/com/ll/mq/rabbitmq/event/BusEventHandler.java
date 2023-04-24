package com.ll.mq.rabbitmq.event;

/**
 * 业务模块消息处理器接口
 */
public interface BusEventHandler {
    void onMessage(EventObj map);
}
