package com.ll.common.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

public interface SendSmsService {
    /**
     * 发送短信接口
     *
     * @param phoneNumbers  手机号
     * @param templateCode  模板编号
     * @param templateParam 模板参数(JSON字符串)
     * @return
     */
    SendSmsResponse sendShortMessage(String phoneNumbers, String templateCode, String templateParam);

    /**
     * 发送短信接口
     *
     * @param phoneNumbers  手机号
     * @param templateCode  模板编号
     * @param templateParam 模板参数(JSON字符串)
     * @param isVirtual     为真，返回虚拟数据
     * @return
     */
    SendSmsResponse sendShortMessage(String phoneNumbers, String templateCode, String templateParam, boolean isVirtual);
}
