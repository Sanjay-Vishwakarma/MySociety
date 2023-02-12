package com.pz.mysociety.entity.userEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity(name = "User")
@Table(name = "user_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "password")
    private String password;

    @NotBlank(message = ValidationMessages.Role_Required)
    @Column(name = "role")
    private String role;

    @Column(name = "otp_validate")
    private int validateOtp;

    @Column(name = "otp_duration")
    private Date otpRequestedTime;

    @Column(name = "otp_attempt")
    private int otpAttempt;

    @Column(name = "fail_attempt")
    private Date failAttempt;

    @Column(name = "user_status")
    private String userStatus;

    @Column(name = "private_key")
    private String key;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "token")
    private String token;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "photo")
    private String photo;

    @Column(name = "is_call")
    private boolean isCall;

    @Column(name = "user_profession")
    private String userProfession;

    @Column(name = "user_specialization")
    private String userSpecialization;

    @Column(name = "user_interest")
    private String userInterest;

    @Column(name = "user_about")
    private String userAbout;

    @Column(name = "user_website")
    private String userWebsite;

    @Column(name = "user_facebook_profile")
    private String facebookProfile;

    @Column(name = "user_twitter_profile")
    private String twitterProfile;

    @Column(name = "user_linkedin_profile")
    private String linkedInProfile;

    @Column(name = "user_instagram_profile")
    private String instagramProfile;


    @Column(name = "timestamp")
    private String timestamp;

    public UserEntity(String name, String email, String mobileNumber, String photo) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.photo = photo;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsCall() {
        return isCall;
    }

    public void setIsCall(boolean isCall) {
        this.isCall = isCall;
    }


}
