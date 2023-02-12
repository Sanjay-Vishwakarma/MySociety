package com.pz.mysociety.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Component
@Slf4j
public class LoadPropertiesUtil {

    public static ResourceBundle getProperty(String propertiesClass)
    {
        log.debug("begin of getProperty");
        ResourceBundle property = null;
        try
        {
            property = ResourceBundle.getBundle(propertiesClass, Locale.getDefault());
        }
        catch (MissingResourceException mre)
        {
            log.debug("Properties not found:" + mre.toString());
        }
        log.debug("end of getProperty");
        return property;
    }

    public static ResourceBundle getProperty(String propertiesClass,String language)
    {
        log.debug("begin of getProperty");
        ResourceBundle property = null;
        try
        {
            if(Functions.nonNullString(language))
            {
                Locale locale = new Locale(language);
                property = ResourceBundle.getBundle(propertiesClass, locale);
            }
            else
            {
                property = ResourceBundle.getBundle(propertiesClass, Locale.getDefault());
            }
        }
        catch (MissingResourceException mre)
        {
            log.debug("Properties not found:" + mre.toString());
        }
        log.debug("end of getProperty");
        return property;
    }

    public static ResourceBundle getProperty(String propertiesClass,String language,String country)
    {
        log.debug("begin of getProperty");
        ResourceBundle property = null;
        try
        {
            if(Functions.nonNullString(language) && Functions.nonNullString(country))
            {
                Locale locale = new Locale(language,country);
                property = ResourceBundle.getBundle(propertiesClass, locale);
            }
            else
            {
                property = ResourceBundle.getBundle(propertiesClass, Locale.getDefault());
            }
        }
        catch (MissingResourceException mre)
        {
            log.debug("Properties not found:" + mre.toString());
        }
        log.debug("end of getProperty");
        return property;
    }

}
