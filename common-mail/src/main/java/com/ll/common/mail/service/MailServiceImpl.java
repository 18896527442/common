package com.ll.common.mail.service;

import com.ll.common.mail.exception.MailApiException;
import com.ll.common.mail.dto.MailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.util.CollectionUtils;
import java.net.URL;

@Service
public class MailServiceImpl implements MailService {

    @Value("${mailAccount}")
    private String  mailAccount;

    @Value("${mailPassword}")
    private String mailPassword;

    private Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public boolean sendMailByAdmin(MailDto mailDto) throws MailApiException {
        boolean isSuccess=false;

        try {
            // 得到回话对象
            Session session = Session.getInstance(getProperties());
            // 获取邮件对象
            Message message = new MimeMessage(session);
            // 设置发件人邮箱地址
            String personal="pushplus 推送加";
            if(mailDto!=null && !StringUtils.isEmpty(mailDto.getPersonal())){
                personal=mailDto.getPersonal();
            }
            message.setFrom(new InternetAddress(mailAccount, personal, "UTF-8"));
            // 设置收件人邮箱地址
            List<InternetAddress> internetAddressList=new ArrayList<>();
            if(mailDto.getToAddress()!=null&&mailDto.getToAddress().size()>0){
                mailDto.getToAddress().forEach((toAddress)->{
                    try {
                        internetAddressList.add(new InternetAddress(toAddress));
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                });
            }
            message.addRecipients(Message.RecipientType.TO, internetAddressList.toArray(new InternetAddress[internetAddressList.size()]));

            // 设置抄送人邮箱地址
            List<InternetAddress> ccList=new ArrayList<>();
            if(mailDto.getCcAddress()!=null&&mailDto.getCcAddress().size()>0){
                mailDto.getCcAddress().forEach((ccAddress)->{
                    try {
                        ccList.add(new InternetAddress(ccAddress));
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                });
            }
            message.addRecipients(Message.RecipientType.CC, ccList.toArray(new InternetAddress[ccList.size()]));

            //设置密送人邮箱地址
            List<InternetAddress> bccList=new ArrayList<>();
            if(mailDto.getBccAddress()!=null&&mailDto.getBccAddress().size()>0){
                mailDto.getBccAddress().forEach((bccAddress)->{
                    try {
                        bccList.add(new InternetAddress(bccAddress));
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                });
            }
            message.addRecipients(Message.RecipientType.BCC, bccList.toArray(new InternetAddress[bccList.size()]));

            // 设置邮件标题
            message.setSubject(mailDto.getSubject());
            // 设置邮件内容
            //message.setText(mailDto.getBody());

            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
//            Multipart mainPart = new MimeMultipart();
//            // 创建一个包含HTML内容的MimeBodyPart
//            BodyPart html = new MimeBodyPart();
//            // 设置HTML内容
//            html.setContent(mailDto.getBody(), "text/html; charset=utf-8");
//            mainPart.addBodyPart(html);
//            // 为邮件添加附件
//            String attachFileNames = mailDto.getAttachment();
//            if (!StringUtils.isEmpty(attachFileNames)) {
//                try {
//                    // 存放邮件附件的MimeBodyPart
//                    MimeBodyPart attachment = new MimeBodyPart();
//                    // 根据附件文件创建文件数据源
//                    File file = new File(attachFileNames);
//                    FileDataSource fds = new FileDataSource(file);
//                    attachment.setDataHandler(new DataHandler(fds));
//                    // 为附件设置文件名
//                    attachment.setFileName(MimeUtility.encodeWord(
//                            file.getName(), "utf-8", null));
//                    mainPart.addBodyPart(attachment);
//                }
//                catch (exception ex){
//                    ex.printStackTrace();
//                    logger.error("邮件附件异常："+ex.getMessage());
//                }
//            }
//            // 将MiniMultipart对象设置为邮件内容
//            message.setContent(mainPart);

            message.setContent(getMultipart(mailDto.getBody(),mailDto.getLocalAttachment(),mailDto.getRemoteAttachment()));

            // 得到邮差对象
            Transport transport = session.getTransport();
            // 连接自己的邮箱账户
            // 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
            transport.connect(mailAccount, mailPassword);
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            isSuccess=true;
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.error("邮件发送异常："+ex.getMessage());
            isSuccess=false;
            throw new MailApiException(500,ex.getMessage());
        }

        return  isSuccess;
    }

    @Override
    public boolean startSystemSendMail(String applicationName,String text){
        MailDto mailDto=new MailDto();
        List<String> toAddress=new ArrayList<>();
        toAddress.add("project@hxtrip.com");   //项目启动专属群组邮箱
        mailDto.setToAddress(toAddress);
        mailDto.setSubject(applicationName+"项目启动成功");

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String mainText= applicationName+ "项目于"+ dateFormat.format(now) +"启动成功！<br/>"+text;
        mailDto.setBody(mainText);

        return sendMailByAdmin(mailDto);
    }


    /**
     * 获取properties
     * @return
     * @throws Exception
     */
    private Properties getProperties() throws Exception{
        //1. 用于存放 SMTP 服务器地址等参数
        Properties properties = new Properties();
        // 主机地址
        properties.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        //properties.setProperty("mail.smtp.host", conf.getConfigItem("mail.smtp.host"));
        // 邮件协议
        properties.setProperty("mail.transport.protocol", "smtp");
        // 认证
        properties.setProperty("mail.smtp.auth", "true");
        // 端口
        properties.setProperty("mail.smtp.port", "465");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        return properties;
    }

    /**
     * 获取Multipart对象，包含正文内容、附件
     * @return
     */
    private Multipart getMultipart(String content, List<String> file, List<String> remoteFile) throws Exception {
        Multipart multipart = new MimeMultipart();
        // 1、向multipart中添加正文
        BodyPart contentBodyPart = new MimeBodyPart();
        contentBodyPart.setContent(content, "text/html;charset=UTF-8");
        multipart.addBodyPart(contentBodyPart);
        // 2、向multipart中添加附件
        if (!CollectionUtils.isEmpty(file))
        {
            for (String url : file) {
                multipart.addBodyPart(getBodyPart(url,getNameByUrl(url),1));
            }
        }
        // 3、向multipart中添加远程附件
        if (!CollectionUtils.isEmpty(remoteFile))
        {
            for (String url : remoteFile){
                multipart.addBodyPart(getBodyPart(encodeURI(url),getNameByUrl(url),2));
            }
        }
        return multipart;
    }

    /**
     * 获取 bodyPart
     * @param path 文件路径
     * @param fileName 文件名称
     * @param pathType 1、本地文件；2、远程文件
     * @return
     */
    private BodyPart getBodyPart(String path,String fileName,int pathType) throws Exception{
        BodyPart bodyPart = new MimeBodyPart();
        // 根据附件路径获取文件,
        DataHandler dh = null;
        if (pathType == 1){
            dh = new DataHandler(new FileDataSource(path));
        }else if (pathType == 2){
            dh = new DataHandler(new URLDataSource(new URL(path)));
        }
        bodyPart.setDataHandler(dh);
        //MimeUtility.encodeWord可以避免文件名乱码
        System.out.println(MimeUtility.encodeWord(fileName));
        bodyPart.setFileName(MimeUtility.encodeWord(fileName,"gb2312",null));
        //bodyPart.setFileName(MimeUtility.encodeWord(fileName));
        return bodyPart;
    }


    /**
     * 对文件fileUrl进行编码 类似于浏览器的encodeURI编码
     * 例子：编码前：http://www.baidu.com/api/resource/robot/word/2/婚前协议.doc
     *      编码后：http://www.baidu.com/api/resource/robot/word/2/%E5%A9%9A%E5%89%8D%E5%8D%8F%E8%AE%AE.doc
     * @param url
     * @return
     */
    private String encodeURI(String url) throws Exception {
        String encode = URLEncoder.encode(url, "UTF-8");

        return encode.replace("%3A",":").replace("%2F","/");
    }

    /**
     * 根据fileUrl获取 带后缀的文件名称
     *
     * @param url
     * @return
     */
    private String getNameByUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
