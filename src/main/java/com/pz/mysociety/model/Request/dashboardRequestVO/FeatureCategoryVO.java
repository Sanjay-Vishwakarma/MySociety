package com.pz.mysociety.model.Request.dashboardRequestVO;

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
public class FeatureCategoryVO extends RequestVO {

    private int id;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(max = VariableSize.MAX_REDIRECT_SIZE, message = ValidationMessages.Redirect_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Redirect_Format_Error)
    private String redirect;

    private boolean isActive;

    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Action_Format_Error)
    private String action;

    public FeatureCategoryVO(int id, String name, String redirect, boolean isActive) {
        this.id = id;
        this.name = name;
        this.redirect = redirect;
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
