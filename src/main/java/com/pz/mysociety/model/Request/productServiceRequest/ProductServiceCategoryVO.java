package com.pz.mysociety.model.Request.productServiceRequest;

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
public class ProductServiceCategoryVO extends RequestVO {

    private int id;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Type_Format_Error)
    @Size(max = 50, message = ValidationMessages.Type_Length_Error)
    private String type;

//    @Size(max = VariableSize.MAX_ICON_URL_SIZE, message = ValidationMessages.Icon_Url_Length_Error)
//    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Icon_Url_Format_Error)
    private String iconUrl;

    private boolean isActive;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
