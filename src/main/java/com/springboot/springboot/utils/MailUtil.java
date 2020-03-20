package com.springboot.springboot.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 邮件发送工具类
 */
@Component
@Slf4j
public class MailUtil {
    @Value("${spring.mail.username}")
    private String FROM; // 邮件发送者

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration configuration;

    /**
     * 普通文本邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(FROM);
            //邮件接收人
            simpleMailMessage.setTo(to);
            //邮件主题
            simpleMailMessage.setSubject(subject);
            //邮件内容
            simpleMailMessage.setText(content);
            sender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
        }
    }

    /**
     * html邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendHTMLMail(String to, String subject, String content) {
        log.info("发送HTML邮件开始：{},{},{}", to, subject, content);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);//true代表支持html
            sender.send(message);
            log.info("发送HTML邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
        }
    }

    /**
     * 带附件多个
     * @param to
     * @param subject
     * @param content
     * @param fileArr
     */
    public void sendAttachmentMail(String to, String subject, String content, String... fileArr) {
        log.info("发送带附件邮件开始：{},{},{},{}", to, subject, content, fileArr);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            // 添加附件
            for (String filePath : fileArr ){
                FileSystemResource file = new FileSystemResource(new File(filePath));
                if (file.exists()) {
                    String fileName = file.getFilename();
                    helper.addAttachment(fileName, file);
                }
            }
            sender.send(message);
            log.info("发送带附件邮件成功");
        } catch (MessagingException e) {
            log.error("发送带附件邮件失败", e);
        }
    }


    /**
     * 带图片多个
     * @param to
     * @param subject
     * @param content
     * @param imgMap
     */
    public void sendInlineResourceMail(String to, String subject, String content, Map<String, String> imgMap) {
        log.info("发送带图片邮件开始：{},{},{},{}", to, subject, content, imgMap);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            // 添加图片
            for (Map.Entry<String, String> entry : imgMap.entrySet()) {
                FileSystemResource res = new FileSystemResource(new File(entry.getValue()));
                if (res.exists()) {
                    helper.addInline(entry.getKey(), res);
                }
            }
            sender.send(message);
            log.info("发送带图片邮件成功");
        } catch (MessagingException e) {
            log.error("发送带图片邮件失败", e);
        }
    }


    /**
     * freemarker模板发送
     * @param to
     * @param subject
     * @param paramMap
     * @param template
     */
    public void sendTemplateMail(String to, String subject, Map<String, Object> paramMap, String template) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setSubject(subject);
            try {
                Template template1 = configuration.getTemplate(template);
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, paramMap);
                helper.setText(html, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sender.send(message);
            log.info("发送模板邮件成功");
        } catch (MessagingException e) {
            log.error("发送模板邮件失败", e);
        }

    }


}





