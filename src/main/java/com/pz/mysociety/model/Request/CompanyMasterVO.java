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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CompanyMasterVO extends RequestVO {

    private int id;

    @Size(max = 50, message = ValidationMessages.Company_Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Type_Format_Error)
    @Size(max = 30, message = ValidationMessages.Type_Length_Error)
    private String companyType;

    private int companyTypeId;

    private String companyLogo;

    private boolean isActive;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
