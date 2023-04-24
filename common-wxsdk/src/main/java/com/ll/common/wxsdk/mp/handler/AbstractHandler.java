package com.ll.common.wxsdk.mp.handler;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
public abstract class AbstractHandler implements WxMpMessageHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());
}
