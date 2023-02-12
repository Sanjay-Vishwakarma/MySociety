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
public class HelperTypeVO extends RequestVO {
    private int id;
    private int serviceTypeId;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Type_Format_Error)
    @Size(max = 30, message = ValidationMessages.Type_Length_Error)
    private String type;

    private String languageType;

    private boolean isFullTime;

    private boolean isActive;

    private String iconImage;

    public boolean getIsFullTime() {
        return isFullTime;
    }

    public void setIsFullTime(boolean isFullTime) {
        this.isFullTime = isFullTime;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public HelperTypeVO(int id, String type, String iconImage, boolean isFullTime, boolean isActive) {
        this.id = id;
        this.type = type;
        this.iconImage = iconImage;
        this.isFullTime = isFullTime;
        this.isActive = isActive;
    }
}
