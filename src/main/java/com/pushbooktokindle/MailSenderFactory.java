package com.pushbooktokindle;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @auth:huzhongying
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MailSenderFactory {
    private JavaMailSender javaMailSender;

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    /**
     * 简单邮件发送
     * @param bmd
     */
    public void senderSimpleMail(BaseMailDefined bmd){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(bmd.getFrom());
        msg.setText(bmd.getText());
        msg.setTo(bmd.getTo());
        msg.setSubject(bmd.getSubject());
        javaMailSender.send(msg);
    }

    /**
     * 发送添加附件的邮件
     * @param bmd
     * @throws MessagingException
     */
    public void senderMimeMail(BaseMailDefined bmd) throws MessagingException{
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(bmd.getTo());
        helper.setText(bmd.getText());
        helper.setFrom(bmd.getFrom());
        helper.setSubject(bmd.getSubject());
        helper.setSentDate(new Date());
        //夹带附件
        FileSystemResource file = new FileSystemResource(new File("E:/task/ip2.rar"));
        helper.addAttachment("ip2.rar", file);
        javaMailSender.send(msg);
    }

    /**
     * 发送内嵌图片或html的邮件
     * @param bmd
     * @throws MessagingException
     */
    public void senderHtmlMail(BaseMailDefined bmd) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(bmd.getTo());
        helper.setText(bmd.getText());
        helper.setFrom(bmd.getFrom());
        helper.setSubject(bmd.getSubject());
        helper.setSentDate(new Date());
        //内嵌式图片或html样式
        helper.setText("<html><body><img src='cid:identifier1235'></body></html>", true);
        FileSystemResource res = new FileSystemResource(new File("E:/task/ip2.png"));
        helper.addInline("identifier1235", res);
        javaMailSender.send(msg);
    }
}
