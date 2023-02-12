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

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyUserMasterVO extends RequestVO{

    private int id;
    private int societyId;
    private int societyUserId;

    @Size(max = 50, message = ValidationMessages.Status_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Status_Format_Error)
    private String adminStatus;

    @Size(max = 500, message = ValidationMessages.Message_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum ,message = ValidationMessages.Message_Format_Error)
    private String message;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number,message = ValidationMessages.Mobile_Format_Error)
    private String mobileNumber;

    @Size(max = 150, message = ValidationMessages.Society_Name_Length_Error)
    @Pattern(regexp= ValidationRegEx.Address,message = ValidationMessages.Society_Name_Format_Error)
    private String societyName;

    @Size(max = 50, message = ValidationMessages.City_Length_Error)
    @Pattern(regexp = ValidationRegEx.City,message = ValidationMessages.City_Format_Error)
    private String city;

    @Size(max = 6, message = ValidationMessages.Pincode_Length_Error)
    @Pattern(regexp=ValidationRegEx.Number,message = ValidationMessages.Pincode_Format_Error)
    private String pincode;

    private boolean isActive;

    private String timestamp;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
