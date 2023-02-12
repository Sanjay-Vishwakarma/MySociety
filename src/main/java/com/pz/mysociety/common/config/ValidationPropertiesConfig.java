package com.pz.mysociety.common.config;

import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.util.Functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Map;


@Configuration
@PropertySource("classpath:/esapi/validation.properties")
public class ValidationPropertiesConfig {

    @Autowired
    private Environment env;


    public final String getValue(String key)
    {

            return Functions.nonNullString(env.getProperty(key))?env.getProperty(key):"";

    }





}
