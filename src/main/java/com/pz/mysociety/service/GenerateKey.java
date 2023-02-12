package com.pz.mysociety.service;


import java.util.Date;
import java.util.Random;

public class GenerateKey {

    public static String generateKey(int size)
    {
        String pwdData = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int len = pwdData.length();
        Date date = new Date();
        Random rand = new Random(date.getTime());
        StringBuffer key = new StringBuffer();
        int index = -1;

        for (int i = 0; i < size; i++)
        {
            index = rand.nextInt(len);
            key.append(pwdData.substring(index, index + 1));
        }
        return key.toString();
    }
}
