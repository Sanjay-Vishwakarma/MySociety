package com.pz.mysociety.model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationRequest {

    private String title;
    private String message;
    private String type;
    private String token;
}
