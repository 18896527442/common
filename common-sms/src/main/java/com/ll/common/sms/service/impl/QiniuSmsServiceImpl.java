package com.ll.common.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.utils.StringUtils;
import com.ll.common.sms.constant.ChannelTypeConstant;
import com.ll.common.sms.dto.response.*;
import com.ll.common.sms.exception.SmsApiException;
import com.ll.common.sms.service.SmsService;
import com.ll.common.sms.util.QiniuSmsManager;
import com.ll.common.sms.dto.request.QuerySendDetailsRequestDto;
import com.ll.common.sms.dto.request.SmsRequestDto;
import com.ll.common.sms.dto.request.TemplateParamDto;
import com.ll.common.sms.dto.response.*;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@ConditionalOnProperty(value = "qiniu.accessKey")
@Service("QiniuSmsServiceImpl")
public class QiniuSmsServiceImpl implements SmsService {

    private Logger logger = LoggerFactory.getLogger(QiniuSmsServiceImpl.class);

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;


    /**
     * 发送单条短信
     *
     * @return
     */
    @Override
    public SmsResponseDto sendSingleSms(SmsRequestDto smsRequestDto) throws SmsApiException {
        if (smsRequestDto == null) {
            throw new SmsApiException(999, "参数不能为空");
        }
        if (!(smsRequestDto.getPhoneNumber() != null && smsRequestDto.getPhoneNumber().length > 0)) {
            throw new SmsApiException(999, "手机号码不能为空");
        }
        if (StringUtils.isEmpty(smsRequestDto.getTemplateCode())) {
            throw new SmsApiException(999, "短信模板ID");
        }

        Auth auth = Auth.create(accessKey, secretKey);
        QiniuSmsManager smsManager = new QiniuSmsManager(auth);

        try {
            //模板参数内容
            Map<String, String> map = new HashMap<String, String>();
            for (TemplateParamDto templateParamDto : smsRequestDto.getTemplateParam()) {
                map.put(templateParamDto.getParamName(), templateParamDto.getParamValue());
            }

            String phoneNumber = smsRequestDto.getPhoneNumber()[0];
            Response resp = smsManager.sendSingleMessage(smsRequestDto.getTemplateCode(), phoneNumber, map);

            SmsResponseDto smsResponseDto = new SmsResponseDto();
            if (resp != null && StringUtils.isNotEmpty(resp.bodyString())) {
                QiniuSmsMessageIdDto qiniuSmsMessageIdDto = JSON.parseObject(resp.bodyString(), QiniuSmsMessageIdDto.class);
                if (qiniuSmsMessageIdDto != null) {
                    smsResponseDto.setBizId(qiniuSmsMessageIdDto.getMessageId());
                }
            }
            smsResponseDto.setRequestId(resp.reqId);
            smsResponseDto.setCode(String.valueOf(resp.statusCode));
            smsResponseDto.setMessage(resp.error);

            return smsResponseDto;

        } catch (QiniuException e) {
            e.printStackTrace();
            logger.error("七牛云短信异常：" + e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("七牛云短信异常：" + ex.getMessage());
            throw new SmsApiException(500, ex.getMessage());
        }
    }

    /**
     * 发送多条短信
     *
     * @param smsRequestDto
     * @return
     * @throws SmsApiException
     */
    @Override
    public SmsResponseDto sendMultiSms(SmsRequestDto smsRequestDto) throws SmsApiException {
        if (smsRequestDto == null) {
            throw new SmsApiException(999, "参数不能为空");
        }
        if (!(smsRequestDto.getPhoneNumber() != null && smsRequestDto.getPhoneNumber().length > 0)) {
            throw new SmsApiException(999, "手机号码不能为空");
        }
        if (StringUtils.isEmpty(smsRequestDto.getTemplateCode())) {
            throw new SmsApiException(999, "短信模板ID");
        }

        Auth auth = Auth.create(accessKey, secretKey);
        SmsManager smsManager = new SmsManager(auth);

        try {
            //模板参数内容
            Map<String, String> map = new HashMap<String, String>();
            for (TemplateParamDto templateParamDto : smsRequestDto.getTemplateParam()) {
                map.put(templateParamDto.getParamName(), templateParamDto.getParamValue());
            }

            Response resp = smsManager.sendMessage(smsRequestDto.getTemplateCode(), smsRequestDto.getPhoneNumber(), map);

            SmsResponseDto smsResponseDto = new SmsResponseDto();
            if (resp != null && StringUtils.isNotEmpty(resp.bodyString())) {
                QiniuSmsJobIdDto sendSmsJobIdDto = JSON.parseObject(resp.bodyString(), QiniuSmsJobIdDto.class);
                if (sendSmsJobIdDto != null) {
                    smsResponseDto.setBizId(sendSmsJobIdDto.getJobId());
                }
            }
            smsResponseDto.setRequestId(resp.reqId);
            smsResponseDto.setCode(String.valueOf(resp.statusCode));
            smsResponseDto.setMessage(resp.error);

            return smsResponseDto;

        } catch (QiniuException e) {
            e.printStackTrace();
            logger.error("七牛云短信异常：" + e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("七牛云短信异常：" + ex.getMessage());
            throw new SmsApiException(500, ex.getMessage());
        }
    }

    /**
     * 查询单条短信结果
     *
     * @param querySendDetailsRequestDto
     * @return
     * @throws SmsApiException
     */
    @Override
    public SmsSendDetailDto querySingleSmsResult(QuerySendDetailsRequestDto querySendDetailsRequestDto) throws SmsApiException {
        if (querySendDetailsRequestDto == null) {
            throw new SmsApiException(999, "参数不能为空");
        }
        if (StringUtils.isEmpty(querySendDetailsRequestDto.getBizId())) {
            throw new SmsApiException(999, "短信消息id不能为空");
        }

        Auth auth = Auth.create(accessKey, secretKey);
        QiniuSmsManager smsManager = new QiniuSmsManager(auth);

        try {
            Response resp = smsManager.queryMessage(querySendDetailsRequestDto.getOutId(),
                    querySendDetailsRequestDto.getBizId(),
                    querySendDetailsRequestDto.getPhoneNumber());

            SmsSendDetailDto smsResponseDto = new SmsSendDetailDto();
            if (resp != null && StringUtils.isNotEmpty(resp.bodyString())) {
                QiniuQueryResultResponseDto qiniuQueryResultResponseDto = JSON.parseObject(resp.bodyString(), QiniuQueryResultResponseDto.class);
                if (qiniuQueryResultResponseDto != null && qiniuQueryResultResponseDto.getItems().size() > 0) {
                    QiniuQueryResultItemResponseDto qiniuQueryResultItemResponseDto = qiniuQueryResultResponseDto.getItems().get(0);

                    if ("DELIVRD".equals(qiniuQueryResultItemResponseDto.getStatus().toUpperCase())) {
                        //发送成功
                        smsResponseDto.setSendStatus(2);
                    } else if ("FAILED".equals(qiniuQueryResultItemResponseDto.getStatus().toUpperCase())) {
                        //发送失败
                        smsResponseDto.setSendStatus(3);
                    } else {
                        //暂时不知道发送中状态
                        logger.info("七牛云短信发送中状态:" + qiniuQueryResultItemResponseDto.getStatus());
                        smsResponseDto.setSendStatus(1);
                    }

                    smsResponseDto.setContent(qiniuQueryResultItemResponseDto.getContent());
                    if (qiniuQueryResultItemResponseDto.getCreatedAt() != null) {
                        smsResponseDto.setSendDate(DateUtils.formatDate(qiniuQueryResultItemResponseDto.getCreatedAt()));
                    }
                    smsResponseDto.setErrCode(qiniuQueryResultItemResponseDto.getError());
                    smsResponseDto.setPhoneNum(qiniuQueryResultItemResponseDto.getMobile());
                    if (qiniuQueryResultItemResponseDto.getDelivrdAt() != null) {
                        smsResponseDto.setReceiveDate(DateUtils.formatDate(qiniuQueryResultItemResponseDto.getDelivrdAt()));
                    }
                    //传入jobId
                    smsResponseDto.setOutId(qiniuQueryResultItemResponseDto.getJobId());
                }
            }
            return smsResponseDto;

        } catch (QiniuException e) {
            e.printStackTrace();
            logger.error("七牛云短信异常：" + e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("七牛云短信异常：" + ex.getMessage());
            throw new SmsApiException(500, ex.getMessage());
        }

    }

    @Override
    public ChannelTypeConstant getChannelType() {
        return ChannelTypeConstant.QiniuSMS;
    }

//    @Override
//    public SmsPageResponse<SmsSendDetailDto> querySendDetails(SmsPageRequest<QuerySendDetailsRequestDto> pageRequest) {
//        return null;
//    }
}
