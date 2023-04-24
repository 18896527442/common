package com.ll.common.sms.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import com.ll.common.sms.SendSmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendSmsServiceImpl implements SendSmsService {

    private Logger logger = LoggerFactory.getLogger(SendSmsServiceImpl.class);

    @Value("${aliyun.sms.defaultConnectTimeout}")
    private String defaultConnectTimeout;

    @Value("${aliyun.sms.defaultReadTimeout}")
    private String defaultReadTimeout;

    //短信API产品名称（短信产品名固定，无需修改）
    @Value("${aliyun.sms.product}")
    private String product;

    //短信API产品域名（接口地址固定，无需修改）
    @Value("${aliyun.sms.domain}")
    private String domain;

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.signName}")
    private String signName;


    @Override
    public SendSmsResponse sendShortMessage(String phoneNumbers, String templateCode, String templateParam) {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", defaultConnectTimeout);
        System.setProperty("sun.net.client.defaultReadTimeout", defaultReadTimeout);
        SendSmsResponse sendSmsResponse = null;

        try {
            //初始化ascClient,暂时不支持多region（请勿修改）
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //使用post提交
            request.setMethod(MethodType.POST);
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
            request.setPhoneNumbers(phoneNumbers);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            request.setTemplateParam(templateParam);
            //请求失败这里会抛ClientException异常
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            logger.error("发送短信失败", e);
        }
        return sendSmsResponse;
    }

    @Override
    public SendSmsResponse sendShortMessage(String phoneNumbers, String templateCode, String templateParam, boolean isVirtual) {
        if (isVirtual) {
            SendSmsResponse sendSmsResponse = new SendSmsResponse();
            sendSmsResponse.setCode("OK");
            sendSmsResponse.setMessage("提交成功");
            sendSmsResponse.setBizId("1345234351232");
            sendSmsResponse.setRequestId("89065826722");
            return sendSmsResponse;
        } else {
            return sendShortMessage(phoneNumbers, templateCode, templateParam);
        }
    }
}
