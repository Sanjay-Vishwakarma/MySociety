package com.pz.mysociety.common.mail;

import com.pz.mysociety.common.util.Functions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Admin on 9/1/2021.
 */
@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine emailTemplateEngine;


    @Value("${mail.fromEmail}")
    String fromEmail;

    @Value("${mail.fromName}")
    String fromName;

    @Value("${mail.logo}")
    String logoName;

    @Value("${mail.firstcolor}")
    String colorName;

    @Value("${mail.companyName}")
    String companyName;

    @Value("${mail.supportEmail}")
    String supportEmail;







    public void sendMail(String toEmail,String subject,String body,String fromEmail, String fromName,String[] CC,String[] Bcc){

        boolean flag = false;

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");

            helper.setFrom(Functions.nonNullString(fromEmail)?fromEmail:this.fromEmail,
                    Functions.nonNullString(fromName)?fromName:this.fromName);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            log.info("==="+body);
            helper.setText(body, true);

            if(Objects.nonNull(CC) && CC.length>0)
            {
                helper.setCc(CC);
            }

            if(Objects.nonNull(Bcc) && Bcc.length>0)
            {
                helper.setBcc(Bcc);
            }

            //logo
            helper.addAttachment("logo.png", new ClassPathResource(logoName));

            javaMailSender.send(message);
            flag = true;

            log.info("Mail Sent using SendMail");

        } catch (MessagingException e) {

            log.error("Error sending mail :::: Messaging Exception",e);
        } catch (UnsupportedEncodingException e) {
            log.error("Error sending mail :::: UnsupportedEncodingException Exception",e);
        }
        catch (Exception e) {
            log.error("Error sending mail ::::  Exception",e);
        }



    }

    public void sendMultipleMails(String[] toEmail,String subject,String body,String fromEmail, String fromName,String[] CC,String[] Bcc){

        boolean flag = false;

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");

            helper.setFrom(Functions.nonNullString(fromEmail)?fromEmail:this.fromEmail,
                    Functions.nonNullString(fromName)?fromName:this.fromName);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            log.info("==="+body);
            helper.setText(body, true);

            if(Objects.nonNull(CC) && CC.length>0)
            {
                helper.setCc(CC);
            }

            if(Objects.nonNull(Bcc) && Bcc.length>0)
            {
                helper.setBcc(Bcc);
            }

            //logo
            helper.addAttachment("logo.png", new ClassPathResource(logoName));

            javaMailSender.send(message);
            flag = true;

            log.info("Mail Sent using SendMail");

        } catch (MessagingException e) {

            log.error("Error sending mail :::: Messaging Exception",e);
        } catch (UnsupportedEncodingException e) {
            log.error("Error sending mail :::: UnsupportedEncodingException Exception",e);
        }
        catch (Exception e) {
            log.error("Error sending mail ::::  Exception",e);
        }



    }

    public void sendMailWithAttachment(String toEmail,String subject,String body,String fromEmail, String fromName,String[] CC,String[] Bcc, String attachment){

        boolean flag = false;

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");

            helper.setFrom(Functions.nonNullString(fromEmail)?fromEmail:this.fromEmail,
                    Functions.nonNullString(fromName)?fromName:this.fromName);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);

            if(Objects.nonNull(CC) && CC.length>0)
            {
                helper.setCc(CC);
            }

            if(Objects.nonNull(Bcc) && Bcc.length>0)
            {
                helper.setBcc(Bcc);
            }

            //logo
            helper.addAttachment("logo.png", new ClassPathResource(logoName));

            FileSystemResource fileSystem =new FileSystemResource(new File(attachment));

            helper.addAttachment(fileSystem.getFilename(),fileSystem);

            //javaMailSender.send(message);

            flag = true;

            log.info("Mail Sent using SendMail");

        } catch (MessagingException e) {

            log.error("Error sending mail with attachment :::: Messaging Exception",e);
        } catch (UnsupportedEncodingException e) {
            log.error("Error sending mail with attachment:::: UnsupportedEncodingException Exception",e);
        } catch(Exception e)
        {
            log.error("Error sending mail with attachment ::::  Exception",e);
        }



    }


    public String getHtmlBodyUsingThymeleafTemplate(
             Map<String, Object> templateModel, String templateName)
             {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = emailTemplateEngine.process(templateName, thymeleafContext);

        return htmlBody;
    }

    public Map<String, Object> getDefaultModel() {

        //log.info(":::::"+colorName +"::::::::"+companyName+"::::::::::::"+supportEmail+"::::::::::::");

        Map<String, Object> model = new HashMap<>();
        model.put(MailPlaceHolder.FIRSTCOLOR.name(),colorName );
        model.put(MailPlaceHolder.COMPANYNAME.name(),companyName);
        model.put(MailPlaceHolder.SUPPORTEMAIL.name(), supportEmail);
        model.put(MailPlaceHolder.LOGO.name(), "logo.png");

        return model;
    }







}
