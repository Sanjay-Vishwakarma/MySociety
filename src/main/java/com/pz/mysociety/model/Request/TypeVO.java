package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TypeVO extends RequestVO {

    private int id;

    private int unitId;

    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Area_Name_Format_Error)
    private String areaName;

    private int floor;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Type_Format_Error)
    @Size(max = 50, message = ValidationMessages.Type_Length_Error)
    private String type;

    private boolean isMandatory;
    private boolean isActive;

    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Action_Format_Error)
    private String action;

    public boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public TypeVO(int id,String type) {
        this.id = id;
        this.type = type;
    }

    public TypeVO(int id, String type, boolean isActive) {
        this.id = id;
        this.type = type;
        this.isActive = isActive;
    }

    public TypeVO(int id,String type, int floor) {
        this.id = id;
        this.type = type;
        this.floor = floor;
    }

    public TypeVO(int id, int unitId, String areaName,String type) {
        this.id = id;
        this.unitId = unitId;
        this.areaName = areaName;
        this.type = type;
    }

    public TypeVO(int id, int unitId, String areaName,String type, int floor) {
        this.id = id;
        this.unitId = unitId;
        this.areaName = areaName;
        this.type = type;
        this.floor = floor;
    }


}
