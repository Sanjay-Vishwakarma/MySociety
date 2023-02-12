package com.pz.mysociety.common.notification;



import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.model.Request.PushNotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FCMService {

    private final FirebaseMessaging firebaseMessaging;

    public FCMService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

//    private Logger logger = LoggerFactory.getLogger(FCMService.class);


    public String sendNotification(String title, String body, String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(title)
                .setBody(body)
                .build();

//        AndroidNotification androidConfig = AndroidNotification.builder()
        //                .setAndroidConfig(AndroidConfig.builder().setNotification(AndroidNotification.builder().))

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putData("title", title)
                .putData("body", body)
                .putData("type", "Hunter")
                .build();



//        Message message = Message.builder()
//                .setNotification(Notification.builder()
//                        .setTitle(note.getTitle())
//                        .setImage("https://pixabay.com/vectors/click-link-open-symbol-internet-38743/")
//                        .setBody(note.getMessage())
//                        .build())
//                .setAndroidConfig(AndroidConfig.builder()
//                        .setTtl(3600 * 1000)
//                        .setNotification(AndroidNotification.builder()
//                                .setIcon("stock_ticker_update")
//                                .setColor("#f45342")
//                                .build())
//                        .build())
//                .setApnsConfig(ApnsConfig.builder()
//                        .setAps(Aps.builder()
//                                .setBadge(42)
//                                .build())
//                        .build())
//                .setToken(note.getToken())
//                .build();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);

        log.info(jsonOutput);

        return firebaseMessaging.send(message);
    }


    public String actionMessage(PushNotificationRequest note) throws FirebaseMessagingException {

        Message message = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(note.getTitle())
                        .setBody(note.getMessage())
                        .build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setNotification(AndroidNotification.builder()
                                .setClickAction("ComplaintDashboard")
                                .setColor("#f45342")
                                .build())
                        .build())
                .setToken(note.getToken())
                .build();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);

        log.info(jsonOutput);

        return firebaseMessaging.send(message);
    }

    public  String multipleNotification(String title, String body, String action, List<String> tokensList) throws FirebaseMessagingException {

        int a = 1;
        List<String> tokens = new ArrayList<>();
        if(tokensList.size() > 500) {
            a = Functions.getToken(tokensList.size());
        }
        System.out.println(a);

        for (int i =0; i<a; i++){
            int startIndex = i*500;
            int endIndex = startIndex+500;

            if(tokensList.size() < endIndex){
                endIndex = tokensList.size() - 1;
            }
            if(tokensList.size() == 1){
                String token = tokensList.get(0);
                tokens.add(token);
            }else {
                tokens = tokensList.subList(startIndex, endIndex);
            }
            System.out.println(tokens.get(0));

            if(!tokens.isEmpty()){

//                MulticastMessage message = MulticastMessage.builder()
//                        .setNotification(Notification.builder()
//                                .setTitle(title)
//                                .setBody(body)
//                                .build())
//                        .addAllTokens(note)
//                        .build();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setAndroidConfig(AndroidConfig.builder()
//                        .setTtl(3600 * 1000)
                        .setNotification(AndroidNotification.builder()
                                .setClickAction(action)
//                                .setColor("#f45342")
                                .build())
                        .build())
                .addAllTokens(tokensList)
                .build();

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonOutput = gson.toJson(message);

                log.info(jsonOutput);

        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
        System.out.println(response.getSuccessCount() + " messages were sent successfully");
        System.out.println(response.getFailureCount() + " messages were not sent" + response.getResponses());

            }
        }

        return "Done";
    }

    public String multipleActionMessage(String title, String body, String action, int id, String url, List<String> tokensList) throws FirebaseMessagingException {

        int a = 1;
        List<String> tokens = new ArrayList<>();
        if(tokensList.size() > 500) {
            a = Functions.getToken(tokensList.size());
        }
        System.out.println(a);

        for (int i =0; i<a; i++){
            int startIndex = i*500;
            int endIndex = startIndex+499;

            if(tokensList.size() < endIndex){
                endIndex = tokensList.size() - 1;
            }
            if(tokensList.size() == 1){
                String token = tokensList.get(0);
                tokens.add(token);
            }else {
                tokens = tokensList.subList(startIndex, endIndex);
            }
            System.out.println(tokens.get(0));

            if(!tokens.isEmpty()){

                Map<String, String> dataObject = new HashMap<>();

                dataObject.put(NotificationPlaceHolder.TITLE.name(), title);
                dataObject.put(NotificationPlaceHolder.BODY.name(), body);
                if(Functions.nonNullString(action)) {
                    dataObject.put(NotificationPlaceHolder.ACTION.name(), action);
                }
                dataObject.put(NotificationPlaceHolder.ID.name(), Integer.toString(id));
                if(Functions.nonNullString(url)) {
                    dataObject.put(NotificationPlaceHolder.URL.name(), url);
                }

                MulticastMessage message = MulticastMessage.builder()
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build())
//                        .setAndroidConfig(AndroidConfig.builder()
//                                .setNotification(AndroidNotification.builder()
//                                        .setClickAction(action)
//                                        .build())
//                                .build())
                        .putAllData(dataObject)
                        .addAllTokens(tokensList)
                        .build();

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonOutput = gson.toJson(message);

                log.info(jsonOutput);

                BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
                System.out.println(response.getSuccessCount() + " messages were sent successfully");
                System.out.println(response.getFailureCount() + " messages were not sent" + response.getResponses());

            }
        }

        return "Done";
    }



//    public void sendMessageToToken(PushNotificationRequest request)
//            throws InterruptedException, ExecutionException {
//        Message message = getPreconfiguredMessageToToken(request);
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String jsonOutput = gson.toJson(message);
//        String response = sendAndGetResponse(message);
//        logger.info("Sent message to token. Device token: " + request.getToken() + ", " + response+ " msg "+jsonOutput);
//    }
//
//    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
//        return FirebaseMessaging.getInstance().sendAsync(message).get();
//    }
//
//
//    private AndroidConfig getAndroidConfig(String topic) {
//        return AndroidConfig.builder()
//                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
//                .setPriority(AndroidConfig.Priority.HIGH)
//                .setNotification(AndroidNotification.builder()
//                        .setTag(topic).build()).build();
//    }
//    private ApnsConfig getApnsConfig(String topic) {
//        return ApnsConfig.builder()
//                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
//    }
//    private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
//        return getPreconfiguredMessageBuilder(request).setToken(request.getToken())
//                .build();
//    }
//    private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request) {
//        return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic())
//                .build();
//    }
//    private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
//        return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(request.getToken())
//                .build();
//    }
//    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
//        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
//        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
//        return Message.builder()
//                .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(
//                        new Notification(request.getTitle(), request.getMessage()));
//    }
}
