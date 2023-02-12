package com.pz.mysociety;

import com.pz.mysociety.common.mail.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Map;

@SpringBootApplication
public class MySocietyServicesApplication extends SpringBootServletInitializer {

	@Autowired
	MailService mailService;

	@Autowired
	SMSService smsService;

	public static void main(String[] args) {
		SpringApplication.run(MySocietyServicesApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources((MySocietyServicesApplication.class));
	}


//	@EventListener(ApplicationReadyEvent.class)
//	public void sendmail()
//	{
//
//		Map<String,Object> model = mailService.getDefaultModel();
//
//		model.put(MailPlaceHolder.OTP.name(),"1234");
//
//		String body= mailService.getHtmlBodyUsingThymeleafTemplate(model, MailTemplateName.MAIL_USER_SIGNUP_OTP);
//
//		mailService.sendMail("rajkumw@gmail.com","Hi 4",body,null,null,null,null);
//	}
//
//
//
//	public void sendSMS()
//	{
//
//		Map<String,Object> model = smsService.getDefaultModel();
//		model.put(MailPlaceHolder.OTP.name(),"1234");
//
//		String body= smsService.getTextBodyUsingThymeleafTemplate(model, SMSTemplateName.SMS_USER_SIGNUP_OTP);
//
//		smsService.sendSMS("9321536456",body);
//	}

}
