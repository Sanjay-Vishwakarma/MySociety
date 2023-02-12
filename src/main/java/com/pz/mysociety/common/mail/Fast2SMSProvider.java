package com.pz.mysociety.common.mail;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;


/**
 * Created by Admin on 9/3/2021.
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fast2SMSProvider  implements SMSProvider {

    private String providerKey;


    @Override
    public boolean sendSMS(String mobileNumber, String message ) {
        boolean flag = false;

        try {
            HttpResponse<JsonNode> response = Unirest.post("https://www.fast2sms.com/dev/bulkV2")
                    .header("authorization", providerKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body("variables_values=" + message + "&route=otp&numbers=" + mobileNumber)
                    .asJson();

            JSONObject my = response.getBody().getObject();
            flag = my.getBoolean("return");

        } catch (UnirestException e) {
            log.error("Error while sending SMS using Fast2SMSProvider"+e);
        }
        return flag;
    }
}
