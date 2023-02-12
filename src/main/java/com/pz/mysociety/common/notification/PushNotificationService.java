package com.pz.mysociety.common.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushNotificationService {

//    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }


    public void sendPushNotificationToToken(String title, String body, String token) {
        try {
            String messageId = fcmService.sendNotification(title, body, token);
            System.out.println(messageId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendMultipleNotificationToToken(String title, String body, String action, List<String> tokenList) {
        try {
            String messageId = fcmService.multipleNotification(title, body, action, tokenList);
            System.out.println(messageId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendActionNotificationToToken(String title, String body, String action, int id, String url, List<String> tokenList) {
        try {
            String messageId = fcmService.multipleActionMessage(title, body, action, id, url, tokenList);
            System.out.println(messageId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
