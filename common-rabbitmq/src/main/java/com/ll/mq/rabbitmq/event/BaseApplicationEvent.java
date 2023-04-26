package com.ll.mq.rabbitmq.event;

import cn.hutool.core.util.ObjectUtil;
import com.ll.mq.rabbitmq.util.SpringContextHolder;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听远程事件,并分发消息到业务模块消息处理器
 */
@Component
public class BaseApplicationEvent implements ApplicationListener<ReRemoteApplicationEvent> {

    @Override
    public void onApplicationEvent(ReRemoteApplicationEvent jeecgRemoteApplicationEvent) {
        EventObj eventObj = jeecgRemoteApplicationEvent.getEventObj();
        if (ObjectUtil.isNotEmpty(eventObj)) {
            //获取业务模块消息处理器
            BusEventHandler busEventHandler = SpringContextHolder.getHandler(eventObj.getHandlerName(), BusEventHandler.class);
            if (ObjectUtil.isNotEmpty(busEventHandler)) {
                //通知业务模块
                busEventHandler.onMessage(eventObj);
            }
        }
    }

}
