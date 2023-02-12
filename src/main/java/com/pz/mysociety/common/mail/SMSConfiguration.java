package com.pz.mysociety.common.mail;

import com.pz.mysociety.entity.societyEntity.MobileConfigurationEntity;
import com.pz.mysociety.model.Request.MobileProviderVO;
import com.pz.mysociety.repository.societyRepository.MobileConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Admin on 9/3/2021.
 */
@Configuration
public class SMSConfiguration {


//    @Value("${sms.provider.name}")
//    private String providerName;
//
//    @Value("${sms.provider.key}")
//    private String providerKey;

    @Autowired
    private MobileConfigurationRepository mobileConfigurationRepository;


    @Bean
    public SMSProvider getProvider()
    {
        SMSProvider smsProvider;

        MobileConfigurationEntity mobileConfiguration = mobileConfigurationRepository.findByIsActive(true);


        if(mobileConfiguration != null && mobileConfiguration.getProviderName().equalsIgnoreCase("Fast2Sms"))
        {
            smsProvider = new Fast2SMSProvider(mobileConfiguration.getProviderKey());
        }else {
            smsProvider = new Fast2SMSProvider();
        }

        return smsProvider;

    }

}
