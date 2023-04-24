package com.ll.common.mail.dto;

import java.util.List;

public class MailDto {

    /**
     * 收件人地址列表
     */
    private List<String> toAddress;

    /**
     * 抄送人地址列表
     */
    private List<String> ccAddress;

    /**
     * 密送人地址列表
     */
    private List<String> bccAddress;

    /**
     * 邮件标题
     */
    private String subject;

    /**
     * 邮件正文
     */
    private String body;

    /**
     * 附件地址
     */
    private List<String> localAttachment;

    /**
     * 远程附件
     */
    private List<String> remoteAttachment;

    /**
     * 发件人名称
     */
    private String personal;

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public List<String> getToAddress() {
        return toAddress;
    }

    public void setToAddress(List<String> toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(List<String> ccAddress) {
        this.ccAddress = ccAddress;
    }

    public List<String> getBccAddress() {
        return bccAddress;
    }

    public void setBccAddress(List<String> bccAddress) {
        this.bccAddress = bccAddress;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getLocalAttachment() {
        return localAttachment;
    }

    public void setLocalAttachment(List<String> localAttachment) {
        this.localAttachment = localAttachment;
    }

    public List<String> getRemoteAttachment() {
        return remoteAttachment;
    }

    public void setRemoteAttachment(List<String> remoteAttachment) {
        this.remoteAttachment = remoteAttachment;
    }
}
