package com.ll.mq.rabbitmq.event;

import lombok.Data;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 自定义网关刷新远程事件
 *
 * @author : zyf
 * @date :2020-11-10
 */
@Data
public class ReRemoteApplicationEvent extends RemoteApplicationEvent {

    private ReRemoteApplicationEvent() {
    }

    private EventObj eventObj;

    public ReRemoteApplicationEvent(EventObj source, String originService, String destinationService) {
        super(source, originService, destinationService);
        this.eventObj = source;
    }

    public ReRemoteApplicationEvent(EventObj source, String originService) {
        super(source, originService, "");
        this.eventObj = source;
    }
}
