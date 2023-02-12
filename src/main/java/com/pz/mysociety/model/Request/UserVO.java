package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.config.ValidationPropertiesConfig;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserVO extends RequestVO {


//    private static final ResourceBundle RB = LoadPropertiesUtil.getProperty("esapi.validation");


//
//    @Autowired
//    private ValidationPropertiesConfig validationPropertiesConfig;

    private int id;
    private int societyId;


    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Email(message = ValidationMessages.Email_Format_Error)
    private String email;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number,message = ValidationMessages.Mobile_Format_Error)
    private String mobileNumber;

    @Pattern(regexp= ValidationRegEx.Password ,message = ValidationMessages.Password_Format_Error)
    private String password;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Role_Format_Error)
    private String role;

    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Status_Format_Error)
    @Size(message = ValidationMessages.Status_Length_Error)
    private String userStatus;

    private int validateOtp;

    private String deviceToken;

    private String token;

    private String photo;

    private boolean isCall;

    @Size(max = VariableSize.MAX_USER_PROFESSION_SIZE, message = ValidationMessages.User_Profession_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.User_Profession_Format_Error)
    private String userProfession;

    @Size(max = VariableSize.MAX_USER_SPECIALIZATION_SIZE, message = ValidationMessages.User_Specialization_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.User_Specialization_Format_Error)
    private String userSpecialization;

    @Size(max = VariableSize.MAX_USER_INTEREST_SIZE, message = ValidationMessages.User_Interest_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.User_Interest_Format_Error)
    private String userInterest;

    @Size(max = VariableSize.MAX_USER_ABOUT_SIZE, message = ValidationMessages.User_About_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.User_About_Format_Error)
    private String userAbout;

    @Size(max = VariableSize.MAX_USER_WEBSITE_SIZE, message = ValidationMessages.User_Website_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.User_Website_Format_Error)
    private String userWebsite;

    @Size(max = VariableSize.MAX_USER_FACEBOOK_PROFILE_SIZE, message = ValidationMessages.User_Facebook_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.User_Facebook_Format_Error)
    private String facebookProfile;

    @Size(max = VariableSize.MAX_USER_TWITTER_PROFILE_SIZE, message = ValidationMessages.User_Facebook_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.User_Twitter_Format_Error)
    private String twitterProfile;

    @Size(max = VariableSize.MAX_USER_LINEDIN_PROFILE_SIZE, message = ValidationMessages.User_LinkedIn_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.User_LinkedIn_Format_Error)
    private String linkedInProfile;

    @Size(max = VariableSize.MAX_USER_INSTAGRAM_PROFILE_SIZE, message = ValidationMessages.User_Instagram_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.User_Instagram_Format_Error)
    private String instagramProfile;

    @Size(max = 50, message = ValidationMessages.Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.Type_Format_Error)
    private String type;


    @Pattern(regexp = ValidationRegEx.Timestamp, message = ValidationMessages.Timestamp_Format_Error)
    private String timestamp;

    @Pattern(regexp= ValidationRegEx.Password ,message = ValidationMessages.Password_Format_Error)
    private String newPassword;


    public UserVO(boolean isCall, String userProfession, String userSpecialization, String userInterest, String userAbout, String userWebsite, String facebookProfile, String twitterProfile, String linkedInProfile, String instagramProfile){
        this.isCall = isCall;
        this.userProfession = userProfession;
        this.userSpecialization = userSpecialization;
        this.userInterest = userInterest;
        this.userAbout = userAbout;
        this.userWebsite = userWebsite;
        this.facebookProfile = facebookProfile;
        this.twitterProfile = twitterProfile;
        this.linkedInProfile = linkedInProfile;
        this.instagramProfile = instagramProfile;
    }

    public UserVO(String name, String email, String mobileNumber, boolean isCall) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.isCall = isCall;
    }

    public UserVO(String name, String email, String mobileNumber, boolean isCall, String userProfession, String userSpecialization, String userInterest, String userAbout, String userWebsite, String facebookProfile, String twitterProfile, String linkedInProfile, String instagramProfile) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.isCall = isCall;
        this.userProfession = userProfession;
        this.userSpecialization = userSpecialization;
        this.userInterest = userInterest;
        this.userAbout = userAbout;
        this.userWebsite = userWebsite;
        this.facebookProfile = facebookProfile;
        this.twitterProfile = twitterProfile;
        this.linkedInProfile = linkedInProfile;
        this.instagramProfile = instagramProfile;
    }

    public boolean getIsCall() {
        return isCall;
    }

    public void setIsCall(boolean isCall) {
        this.isCall = isCall;
    }
}
