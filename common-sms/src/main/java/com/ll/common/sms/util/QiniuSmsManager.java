package com.ll.common.sms.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.http.MethodType;
import com.qiniu.http.Response;
import com.qiniu.sms.Configuration;
import com.qiniu.sms.model.SignatureInfo;
import com.qiniu.sms.model.TemplateInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlUtils;

import java.util.Map;

public class QiniuSmsManager {

    private final Auth auth;
    private final Client client;
    private Configuration configuration;

    public QiniuSmsManager(Auth auth) {
        this.auth = auth;
        this.configuration = new Configuration();
        this.client = new Client(this.configuration);
    }

    public QiniuSmsManager(Auth auth, Configuration cfg) {
        this.auth = auth;
        this.configuration = cfg.clone();
        this.client = new Client(this.configuration);
    }

    public Response queryMessage(String jobId,String messageId,String mobile) throws QiniuException {
        String requestUrl = String.format("%s/v1/messages", this.configuration.smsHost());

        StringMap queryMap = new StringMap();
        queryMap.putNotEmpty("job_id", jobId);
        queryMap.putNotEmpty("message_id", messageId);
        queryMap.putNotEmpty("mobile", mobile);
        requestUrl = UrlUtils.composeUrlWithQueries(requestUrl, queryMap);
        return this.get(requestUrl);
    }

    public Response sendMessage(String templateId, String[] mobiles, Map<String, String> parameters) throws QiniuException {
        String requestUrl = String.format("%s/v1/message", this.configuration.smsHost());
        StringMap bodyMap = new StringMap();
        bodyMap.put("template_id", templateId);
        bodyMap.put("mobiles", mobiles);
        bodyMap.put("parameters", parameters);
        return this.post(requestUrl, Json.encode(bodyMap).getBytes());
    }

    public Response sendSingleMessage(String templateId, String mobile, Map<String, String> parameters) throws QiniuException {
        String requestUrl = String.format("%s/v1/message/single", this.configuration.smsHost());
        StringMap bodyMap = new StringMap();
        bodyMap.put("template_id", templateId);
        bodyMap.put("mobile", mobile);
        bodyMap.put("parameters", parameters);
        return this.post(requestUrl, Json.encode(bodyMap).getBytes());
    }

    public Response describeSignature(String auditStatus, int page, int pageSize) throws QiniuException {
        String requestUrl = String.format("%s/v1/signature", this.configuration.smsHost());
        StringMap queryMap = new StringMap();
        queryMap.putNotEmpty("audit_status", auditStatus);
        queryMap.putWhen("page", page, page > 0);
        queryMap.putWhen("page_size", pageSize, pageSize > 0);
        requestUrl = UrlUtils.composeUrlWithQueries(requestUrl, queryMap);
        return this.get(requestUrl);
    }

    public SignatureInfo describeSignatureItems(String auditStatus, int page, int pageSize) throws QiniuException {
        Response resp = this.describeSignature(auditStatus, page, pageSize);
        SignatureInfo signatureInfo = (SignatureInfo)Json.decode(resp.bodyString(), SignatureInfo.class);
        return signatureInfo;
    }

    public Response createSignature(String signature, String source, String[] pics) throws QiniuException {
        String requestUrl = String.format("%s/v1/signature", this.configuration.smsHost());
        StringMap bodyMap = new StringMap();
        bodyMap.put("signature", signature);
        bodyMap.put("source", source);
        bodyMap.putWhen("pics", pics, pics.length > 0);
        return this.post(requestUrl, Json.encode(bodyMap).getBytes());
    }

    public Response modifySignature(String signatureId, String signature) throws QiniuException {
        String reqUrl = String.format("%s/v1/signature/%s", this.configuration.smsHost(), signatureId);
        StringMap bodyMap = new StringMap();
        bodyMap.put("signature", signature);
        return this.put(reqUrl, Json.encode(bodyMap).getBytes());
    }

    public Response deleteSignature(String signatureId) throws QiniuException {
        String requestUrl = String.format("%s/v1/signature/%s", this.configuration.smsHost(), signatureId);
        return this.delete(requestUrl);
    }

    public Response describeTemplate(String auditStatus, int page, int pageSize) throws QiniuException {
        String requestUrl = String.format("%s/v1/template", this.configuration.smsHost());
        StringMap queryMap = new StringMap();
        queryMap.putNotEmpty("audit_status", auditStatus);
        queryMap.putWhen("page", page, page > 0);
        queryMap.putWhen("page_size", pageSize, pageSize > 0);
        requestUrl = UrlUtils.composeUrlWithQueries(requestUrl, queryMap);
        return this.get(requestUrl);
    }

    public TemplateInfo describeTemplateItems(String auditStatus, int page, int pageSize) throws QiniuException {
        Response resp = this.describeTemplate(auditStatus, page, pageSize);
        TemplateInfo templateInfo = (TemplateInfo)Json.decode(resp.bodyString(), TemplateInfo.class);
        return templateInfo;
    }

    public Response createTemplate(String name, String template, String type, String description, String signatureId) throws QiniuException {
        String requestUrl = String.format("%s/v1/template", this.configuration.smsHost());
        StringMap bodyMap = new StringMap();
        bodyMap.put("name", name);
        bodyMap.put("template", template);
        bodyMap.put("type", type);
        bodyMap.put("description", description);
        bodyMap.put("signature_id", signatureId);
        return this.post(requestUrl, Json.encode(bodyMap).getBytes());
    }

    public Response modifyTemplate(String templateId, String name, String template, String description, String signatureId) throws QiniuException {
        String requestUrl = String.format("%s/v1/template/%s", this.configuration.smsHost(), templateId);
        StringMap bodyMap = new StringMap();
        bodyMap.put("name", name);
        bodyMap.put("template", template);
        bodyMap.put("description", description);
        bodyMap.put("signature_id", signatureId);
        return this.put(requestUrl, Json.encode(bodyMap).getBytes());
    }

    public Response deleteTemplate(String templateId) throws QiniuException {
        String requestUrl = String.format("%s/v1/template/%s", this.configuration.smsHost(), templateId);
        return this.delete(requestUrl);
    }

    private Response get(String url) throws QiniuException {
        StringMap headers = this.composeHeader(url, MethodType.GET.toString(), (byte[])null, "application/x-www-form-urlencoded");
        return this.client.get(url, headers);
    }

    private Response post(String url, byte[] body) throws QiniuException {
        StringMap headers = this.composeHeader(url, MethodType.POST.toString(), body, "application/json");
        return this.client.post(url, body, headers, "application/json");
    }

    private Response put(String url, byte[] body) throws QiniuException {
        StringMap headers = this.composeHeader(url, MethodType.PUT.toString(), body, "application/json");
        return this.client.put(url, body, headers, "application/json");
    }

    private Response delete(String url) throws QiniuException {
        StringMap headers = this.composeHeader(url, MethodType.DELETE.toString(), (byte[])null, "application/octet-stream");
        return this.client.delete(url, headers);
    }

    private StringMap composeHeader(String url, String method, byte[] body, String contentType) {
        StringMap headers = this.auth.authorizationV2(url, method, body, contentType);
        headers.put("Content-Type", contentType);
        return headers;
    }

}
