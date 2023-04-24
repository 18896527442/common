package com.ll.common.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import com.ll.common.sms.constant.ChannelTypeConstant;
import com.ll.common.sms.dto.response.AliyunQueryResultResponseDto;
import com.ll.common.sms.dto.response.SmsSendDetailDto;
import com.ll.common.sms.exception.SmsApiException;
import com.ll.common.sms.service.SmsService;
import com.ll.common.sms.dto.request.BatchSmsRequestDto;
import com.ll.common.sms.dto.request.QuerySendDetailsRequestDto;
import com.ll.common.sms.dto.request.SmsRequestDto;
import com.ll.common.sms.dto.request.TemplateParamDto;
import com.ll.common.sms.dto.response.SmsResponseDto;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@ConditionalOnProperty(value = "aliyun.sms.accessKeyId")
@Service("AliyunSmsServiceImpl")
public class AliyunSmsServiceImpl implements SmsService {

    private Logger logger = LoggerFactory.getLogger(AliyunSmsServiceImpl.class);

    @Value("${aliyun.sms.defaultConnectTimeout}")
    private String defaultConnectTimeout;

    @Value("${aliyun.sms.defaultReadTimeout}")
    private String defaultReadTimeout;

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.signName}")
    private String signName;

    /**
     * 发送单条短信
     *
     * @param smsRequestDto
     * @return
     * @throws SmsApiException
     */
    @Override
    public SmsResponseDto sendSingleSms(SmsRequestDto smsRequestDto) throws SmsApiException {
        //验证必传字段
        if (smsRequestDto == null) {
            throw new SmsApiException(999, "参数不能为空");
        }
        if (!(smsRequestDto.getPhoneNumber() != null && smsRequestDto.getPhoneNumber().length > 0)) {
            throw new SmsApiException(999, "手机号码不能为空");
        }
        if (StringUtils.isEmpty(smsRequestDto.getTemplateCode())) {
            throw new SmsApiException(999, "短信模板ID");
        }

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("SignName", signName);
        //仅发送第一个手机号码
        String phoneNumber = smsRequestDto.getPhoneNumber()[0];
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("TemplateCode", smsRequestDto.getTemplateCode());

        if (smsRequestDto.getTemplateParam() != null && smsRequestDto.getTemplateParam().size() > 0) {
            StringBuilder templateParam=new StringBuilder();
            templateParam.append("{");
            for (TemplateParamDto templateParamDto : smsRequestDto.getTemplateParam()) {
                templateParam.append("\"").append(templateParamDto.getParamName()).append("\":\"").append(templateParamDto.getParamValue()).append("\",");
            }
            if (templateParam.toString().endsWith(",")) {
                templateParam.substring(0, templateParam.length() - 2);
            }
            templateParam.append("}");
            request.putQueryParameter("TemplateParam", templateParam.toString());

            logger.info("阿里云短信参数内容："+templateParam.toString());
        }
        else {
            logger.warn("阿里云短信参数为空，模板："+ smsRequestDto.getTemplateCode());
        }

        if (StringUtils.isNotEmpty(smsRequestDto.getSmsUpExtendCode())) {
            request.putQueryParameter("SmsUpExtendCode", smsRequestDto.getSmsUpExtendCode());
        }
        if (StringUtils.isNotEmpty(smsRequestDto.getOutId())) {
            request.putQueryParameter("OutId", smsRequestDto.getOutId());
        }

        try {
            CommonResponse response = client.getCommonResponse(request);
            SmsResponseDto smsResponseDto = JSON.parseObject(response.getData(), SmsResponseDto.class);
            return smsResponseDto;
        } catch (ServerException e) {
            e.printStackTrace();
            logger.error("阿里云短信异常：" + e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
            logger.error("阿里云短信异常：" + e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("阿里云短信异常：" + ex.getMessage());
            throw new SmsApiException(500, ex.getMessage());
        }
    }

    /**
     * 发送多人短信
     *
     * @param smsRequestDto
     * @return
     * @throws SmsApiException
     */
    @Override
    public SmsResponseDto sendMultiSms(SmsRequestDto smsRequestDto) throws SmsApiException {
        //验证必传字段
        if (smsRequestDto == null) {
            throw new SmsApiException(999, "参数不能为空");
        }
        if (!(smsRequestDto.getPhoneNumber() != null && smsRequestDto.getPhoneNumber().length > 0)) {
            throw new SmsApiException(999, "手机号码不能为空");
        }
        if (StringUtils.isEmpty(smsRequestDto.getTemplateCode())) {
            throw new SmsApiException(999, "短信模板ID");
        }

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("SignName", signName);
        //多个手机号
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < smsRequestDto.getPhoneNumber().length; i++) {
            sb.append(smsRequestDto.getPhoneNumber()[i].trim() + ",");
        }
        String phoneNumber = sb.toString();
        if (StringUtils.isNotEmpty(phoneNumber)) {
            phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
        }
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("TemplateCode", smsRequestDto.getTemplateCode());

        if (smsRequestDto.getTemplateParam() != null && smsRequestDto.getTemplateParam().size() > 0) {
            String templateParam = "{";
            for (TemplateParamDto templateParamDto : smsRequestDto.getTemplateParam()) {
                templateParam += "\"" + templateParamDto.getParamName() + "\":\"" + templateParamDto.getParamValue() + "\",";
            }
            if (templateParam.endsWith(",")) {
                templateParam.substring(0, templateParam.length() - 2);
            }
            templateParam += "}";
            request.putQueryParameter("TemplateParam", templateParam);
        }

        if (StringUtils.isNotEmpty(smsRequestDto.getSmsUpExtendCode())) {
            request.putQueryParameter("SmsUpExtendCode", smsRequestDto.getSmsUpExtendCode());
        }
        if (StringUtils.isNotEmpty(smsRequestDto.getOutId())) {
            request.putQueryParameter("OutId", smsRequestDto.getOutId());
        }

        try {
            CommonResponse response = client.getCommonResponse(request);
            SmsResponseDto smsResponseDto = JSON.parseObject(response.getData(), SmsResponseDto.class);
            return smsResponseDto;
        } catch (ServerException e) {
            e.printStackTrace();
            logger.error("阿里云短信异常：" + e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
            logger.error("阿里云短信异常：" + e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("阿里云短信异常：" + ex.getMessage());
            throw new SmsApiException(500, ex.getMessage());
        }
    }


    public SmsResponseDto sendBatchSms(BatchSmsRequestDto batchSmsRequestDto) throws SmsApiException {
        //验证必传字段
        if (batchSmsRequestDto == null) {
            throw new SmsApiException(999, "参数不能为空");
        }
        if (StringUtils.isEmpty(batchSmsRequestDto.getPhoneNumberJson())) {
            throw new SmsApiException(999, "手机号码不能为空");
        }
        if (StringUtils.isEmpty(batchSmsRequestDto.getTemplateCode())) {
            throw new SmsApiException(999, "短信模板ID");
        }
        if (StringUtils.isEmpty(batchSmsRequestDto.getSignNameJson())) {
            throw new SmsApiException(999, "短信签名不能为空");
        }

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendBatchSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("SignNameJson", batchSmsRequestDto.getSignNameJson());
        request.putQueryParameter("PhoneNumberJson", batchSmsRequestDto.getPhoneNumberJson());
        request.putQueryParameter("TemplateCode", batchSmsRequestDto.getTemplateCode());

        if (StringUtils.isNotEmpty(batchSmsRequestDto.getTemplateParamJson())) {
            request.putQueryParameter("TemplateParamJson", batchSmsRequestDto.getTemplateParamJson());
        }
        if (StringUtils.isNotEmpty(batchSmsRequestDto.getSmsUpExtendCodeJson())) {
            request.putQueryParameter("SmsUpExtendCodeJson", batchSmsRequestDto.getSmsUpExtendCodeJson());
        }

        try {
            CommonResponse response = client.getCommonResponse(request);
            SmsResponseDto smsResponseDto = JSON.parseObject(response.getData(), SmsResponseDto.class);
            return smsResponseDto;
        } catch (ServerException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        }
    }

    /**
     * 查询单条短信结果
     *
     * @param querySendDetailsRequestDto
     * @return
     */
    @Override
    public SmsSendDetailDto querySingleSmsResult(QuerySendDetailsRequestDto querySendDetailsRequestDto) throws SmsApiException {
        if (querySendDetailsRequestDto == null) {
            throw new SmsApiException(999, "参数不能为空");
        }
        if (StringUtils.isEmpty(querySendDetailsRequestDto.getPhoneNumber())) {
            throw new SmsApiException(999, "手机号码不能为空");
        }
        if (querySendDetailsRequestDto.getSendDate() == null) {
            throw new SmsApiException(999, "短信发送日期不能为空");
        }
        if (StringUtils.isEmpty(querySendDetailsRequestDto.getBizId())) {
            throw new SmsApiException(999, "发送回执ID不能为空");
        }

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("QuerySendDetails");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumber", querySendDetailsRequestDto.getPhoneNumber());
        request.putQueryParameter("SendDate", DateUtils.formatDate(querySendDetailsRequestDto.getSendDate(), "yyyyMMdd"));
        request.putQueryParameter("PageSize", "10");
        request.putQueryParameter("CurrentPage", "1");
        request.putQueryParameter("BizId", querySendDetailsRequestDto.getBizId());
        try {
            CommonResponse response = client.getCommonResponse(request);
            AliyunQueryResultResponseDto querySendDetailsResponseDto = JSON.parseObject(response.getData(), AliyunQueryResultResponseDto.class);

            SmsSendDetailDto aliyunSmsSendDetailDto = new SmsSendDetailDto();
            if (querySendDetailsResponseDto != null && querySendDetailsResponseDto.getAliyunSmsSendDetailDtos() != null) {
                List<SmsSendDetailDto> smsSendDetailDtoList = querySendDetailsResponseDto.getAliyunSmsSendDetailDtos().getSmsSendDetailDto();

                if (smsSendDetailDtoList.size() > 0) {
                    aliyunSmsSendDetailDto = smsSendDetailDtoList.get(0);
                }
            }

            return aliyunSmsSendDetailDto;
        } catch (ServerException e) {
            e.printStackTrace();
            logger.error("阿里云短信异常：" + e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
            logger.error("阿里云短信异常：" + e.getMessage());
            throw new SmsApiException(500, e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("阿里云短信异常：" + ex.getMessage());
            throw new SmsApiException(500, ex.getMessage());
        }

    }

    @Override
    public ChannelTypeConstant getChannelType() {
        return ChannelTypeConstant.AliyunSMS;
    }



//    @Override
//    public SmsPageResponse<SmsSendDetailDto> querySendDetails(SmsPageRequest<QuerySendDetailsRequestDto> pageRequest){
//        if(pageRequest==null||pageRequest.getParams()==null){
//            throw new SmsApiException(999, "参数不能为空");
//        }
//        if(StringUtils.isEmpty(pageRequest.getParams().getPhoneNumber())){
//            throw new SmsApiException(999, "手机号码不能为空");
//        }
//        if(StringUtils.isEmpty(pageRequest.getParams().getSendDate())){
//            throw new SmsApiException(999, "短信发送日期不能为空");
//        }
//
//        if(pageRequest.getCurrent()==null||pageRequest.getCurrent()<=0){
//            pageRequest.setCurrent(1);
//        }
//        if(pageRequest.getPageSize()==null||pageRequest.getPageSize()<=0){
//            pageRequest.setPageSize(10);
//        }
//
//        SmsPageResponse<SmsSendDetailDto> pageResponse=new SmsPageResponse<>();
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("QuerySendDetails");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumber", pageRequest.getParams().getPhoneNumber());
//        request.putQueryParameter("SendDate", pageRequest.getParams().getSendDate());
//        request.putQueryParameter("PageSize", pageRequest.getPageSize().toString());
//        request.putQueryParameter("CurrentPage", pageRequest.getCurrent().toString());
//        request.putQueryParameter("BizId", pageRequest.getParams().getBizId());
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            QuerySendDetailsResponseDto querySendDetailsResponseDto=JSON.parseObject(response.getData(),QuerySendDetailsResponseDto.class);
//
//            pageResponse.setTotal(Integer.parseInt(querySendDetailsResponseDto.getTotalCount()));
//            pageResponse.setPageSize(pageRequest.getPageSize());
//            pageResponse.setPageNum(pageRequest.getCurrent());
//            pageResponse.setList(querySendDetailsResponseDto.getSmsSendDetailDTOs());
//            //计算页面数量
//            int pages=((int)(pageResponse.getTotal()) + pageResponse.getPageSize() - 1) / pageResponse.getPageSize();
//            pageResponse.setPages(pages);
//
//        } catch (ServerException e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new SmsApiException(500,e.getMessage());
//        } catch (ClientException e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new SmsApiException(500,e.getMessage());
//        }
//
//        return pageResponse;
//    }


}
