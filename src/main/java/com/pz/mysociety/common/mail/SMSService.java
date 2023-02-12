package com.pz.mysociety.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 9/2/2021.
 */
@Service
@Slf4j
public class SMSService {

    @Value("${mail.companyName}")
    String companyName;

    @Autowired
    private SpringTemplateEngine emailTemplateEngine;

    @Autowired
    private SMSProvider smsProvider;

    public void sendSMS(String mobileNumber, String message)
    {
        if(smsProvider !=null) {
            smsProvider.sendSMS(mobileNumber, message);
            log.info("SMS Sent");
        }

    }

    public String getTextBodyUsingThymeleafTemplate(
            Map<String, Object> templateModel, String templateName)
    {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String textBody = emailTemplateEngine.process(templateName, thymeleafContext);

        return textBody;
    }

    public Map<String, Object> getDefaultModel() {


        Map<String, Object> model = new HashMap<>();
        model.put(MailPlaceHolder.COMPANYNAME.name(),companyName);

        return model;
    }
}
