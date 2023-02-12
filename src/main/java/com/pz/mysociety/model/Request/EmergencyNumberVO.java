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
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EmergencyNumberVO extends RequestVO {

    private int id;
    private int societyId;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number,message = ValidationMessages.Mobile_Format_Error)
    private String mobileNumber;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Type_Format_Error)
    @Size(max = 30, message = ValidationMessages.Type_Length_Error)
    private String type;

    @Email(message = ValidationMessages.Email_Format_Error)
    @Size(min = 3, max = 100, message = ValidationMessages.Email_Length_Error)
    private String email;

    @Size(max = 500, message = ValidationMessages.Address_Length_Error)
    @Pattern(regexp=ValidationRegEx.Address,message = ValidationMessages.Address_Format_Error)
    private String address;

    private boolean isActive;

    public boolean getIsActive(){return isActive;}

    public void setIsActive(boolean isActive){this.isActive = isActive;}

}
