package com.pz.mysociety.model.Request.userRequestVO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProfessionMasterVO extends RequestVO{

    private int id;
    private int societyId;
    private int unitId;
    private int userId;

    @Size(max = VariableSize.MAX_USER_PROFESSION_SIZE, message = ValidationMessages.User_Profession_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.User_Profession_Format_Error)
    private String profession;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number,message = ValidationMessages.Mobile_Format_Error)
    private String mobileNumber;

    public ProfessionMasterVO(int id, String profession, String name, String mobileNumber) {
        this.id = id;
        this.profession = profession;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }
}
