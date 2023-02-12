package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
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
public class MobileProviderVO extends RequestVO {

    private int id;

    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.Provider_Name_Format_Error)
    @Size(max = 50, message = ValidationMessages.Provider_Name_Length_Error)
    private String providerName;

//    private String providerKey;

    private boolean isActive;

    public boolean getIsActive(){return isActive;}

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }
}
