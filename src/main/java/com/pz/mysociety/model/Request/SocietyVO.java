package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyVO extends RequestVO {

    private int id;

    @Size(max = VariableSize.MAX_REGISTRATION_NUMBER_SIZE, message = ValidationMessages.Registration_Number_Length_Error)
    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Registration_Number_Format_Error)
    private String registrationNumber;

    @Size(max = VariableSize.MAX_EMAIL_SIZE, message = ValidationMessages.Email_Length_Error)
    @Pattern(regexp= ValidationRegEx.Address,message = ValidationMessages.Email_Format_Error)
    private String billingEmail;

    @Size(max = 150, message = ValidationMessages.Society_Name_Length_Error)
    @Pattern(regexp= ValidationRegEx.Address,message = ValidationMessages.Society_Name_Format_Error)
    private String societyName;

    @Pattern(regexp = ValidationRegEx.Address,message = ValidationMessages.Address_Format_Error)
    @Size(max = 500, message = ValidationMessages.Address_Length_Error)
    private String address;

    @Pattern(regexp=ValidationRegEx.SafeString,message = ValidationMessages.Landmark_Format_Error)
    @Size(max = 100, message = ValidationMessages.Landmark_Length_Error)
    private String landmark;

    @Pattern(regexp=ValidationRegEx.SafeString,message = ValidationMessages.Society_Block_Format_Error)
    @Size(max = 100, message = ValidationMessages.Society_Block_Length_Error)
    private String societyBlock;

    @Size(max = 50, message = ValidationMessages.State_Length_Error)
    @Pattern(regexp = ValidationRegEx.State,message = ValidationMessages.State_Format_Error)
    private String state;

    @Size(max = 50, message = ValidationMessages.City_Length_Error)
    @Pattern(regexp = ValidationRegEx.City,message = ValidationMessages.City_Format_Error)
    private String city;

    @Size(max = 6, message = ValidationMessages.Pincode_Length_Error)
    @Pattern(regexp=ValidationRegEx.Number,message = ValidationMessages.Pincode_Format_Error)
    private String pincode;

    @Size(max = 50, message = ValidationMessages.Status_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Status_Format_Error)
    private String societyStatus;

    private String photo;

    private boolean isActive;

    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Action_Format_Error)
    private String action;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
