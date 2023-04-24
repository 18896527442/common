package com.ll.common.sms.service;

import com.ll.common.sms.constant.ChannelTypeConstant;
import com.ll.common.sms.dto.response.SmsSendDetailDto;
import com.ll.common.sms.dto.request.QuerySendDetailsRequestDto;
import com.ll.common.sms.dto.request.SmsRequestDto;
import com.ll.common.sms.dto.response.SmsResponseDto;

public interface SmsService {

    //发送短信
    //发送多条不同签名的短信
    // SmsResponseDto sendBatchSms(BatchSmsRequestDto batchSmsRequestDto);

    //单条发送短信
    SmsResponseDto sendSingleSms(SmsRequestDto smsRequestDto);

    //发送多条短信
    SmsResponseDto sendMultiSms(SmsRequestDto smsRequestDto);

    //查询单条短信发送结果
    SmsSendDetailDto querySingleSmsResult(QuerySendDetailsRequestDto querySendDetailsRequestDto);

    //查询多条发送记录
    //SmsPageResponse<SmsSendDetailDto> querySendDetails(SmsPageRequest<QuerySendDetailsRequestDto> pageRequest);

    /**
     * 获取渠道类型
     *
     * @return
     */
    ChannelTypeConstant getChannelType();



}
