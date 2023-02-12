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
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GuardVO extends RequestVO{

    private int id;
    private int societyId;
    private int helperTypeId;
    private List<HelperDocumentVO> documentList;
    private List<GuardDocumentMasterVO> guardDocumentList;
    private String document;
    //    @Range(min = 0, max = 999999, message = ValidationMessages.Guard_Pin_Error)
    private int guardPin;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(max = 100, message = ValidationMessages.Email_Length_Error)
    @Email(message = ValidationMessages.Email_Format_Error)
    private String email;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number,message = ValidationMessages.Mobile_Format_Error)
    private String mobileNumber;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Role_Format_Error)
    private String role;

    private boolean isActive;

    private int validateOtp;

    private String photo;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
