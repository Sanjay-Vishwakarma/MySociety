package com.pz.mysociety.common.mail;


/**
 * Created by Admin on 9/1/2021.
 */



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class MailConfiguration {


        @Bean
        public TemplateEngine emailTemplateEngine() {
                final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
                // Resolver for TEXT SMS
                templateEngine.addTemplateResolver(smsTemplateResolver());
                // Resolver for HTML emails (except the editable one)
                templateEngine.addTemplateResolver(mailTemplateResolver());
                // Message source, internationalization specific to emails
                templateEngine.setTemplateEngineMessageSource(emailMessageSource());
                return templateEngine;
        }


        @Bean
        public ResourceBundleMessageSource emailMessageSource() {
                final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
                messageSource.setBasename("mailMessages");
                return messageSource;
        }


        @Bean
        public ITemplateResolver mailTemplateResolver() {
                ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
                //templateResolver.setResolvablePatterns(Collections.singleton("/mail/template/*"));
                templateResolver.setPrefix("mail/templates/");
                templateResolver.setSuffix(".html");
                templateResolver.setTemplateMode(TemplateMode.HTML);
                templateResolver.setCharacterEncoding("UTF-8");
                templateResolver.setCacheable(false);
                templateResolver.setOrder(1);
                templateResolver.setCheckExistence(true);
                return templateResolver;
        }

        @Bean
        public ITemplateResolver smsTemplateResolver() {
                final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
                //templateResolver.setResolvablePatterns(Collections.singleton("smsTemplate/*"));
                templateResolver.setPrefix("mail/templates/");
                templateResolver.setSuffix(".txt");
                templateResolver.setTemplateMode(TemplateMode.TEXT);
                templateResolver.setCharacterEncoding("UTF-8");
                templateResolver.setCacheable(false);
                templateResolver.setOrder(1);
                templateResolver.setCheckExistence(true);
                return templateResolver;
        }


        /*private ITemplateResolver fileTemplateResolver() {
                FileTemplateResolver templateResolver = new FileTemplateResolver();
                templateResolver.setPrefix(mailTemplatesPath);
                templateResolver.setSuffix(".template");
                templateResolver.setTemplateMode("HTML");
                templateResolver.setCharacterEncoding("UTF-8");
                return templateResolver;
        }*/



}
