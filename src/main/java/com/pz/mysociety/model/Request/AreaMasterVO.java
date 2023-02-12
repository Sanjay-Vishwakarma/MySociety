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

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AreaMasterVO extends RequestVO {

    private int id;
    private int societyId;
    private int areaTypeId;

    @Size(max = VariableSize.MAX_AREA_SIZE, message = ValidationMessages.Area_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Area_Name_Format_Error)
    private String areaName;

    private int floor;
    private boolean isActive;
    private boolean isParking;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Area_Type_Format_Error)
    private String areaType;

    public boolean getIsParking() {
        return isParking;
    }

    public void setIsParking(boolean isParking) {
        this.isParking = isParking;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
