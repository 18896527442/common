package com.ll.common.mail.service;

import com.ll.common.mail.dto.MailDto;

public interface MailService {

    boolean sendMailByAdmin(MailDto mailDto);

    /**
     * 系统启动通知邮件
     * @param applicationName
     * @param text
     * @return
     */
    boolean startSystemSendMail(String applicationName, String text);

}
