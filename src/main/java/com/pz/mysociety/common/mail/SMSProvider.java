package com.pz.mysociety.common.mail;

/**
 * Created by Admin on 9/3/2021.
 */
public interface SMSProvider {

    public boolean sendSMS(String mobileNumber, String message);
}
