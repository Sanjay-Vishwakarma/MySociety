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
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UnitMasterVO extends RequestVO {

    private int id;
    private int societyId;
    private int areaId;
    private int areaTypeId;
    private String areaType;

    @Size(max = VariableSize.MAX_UNIT_SIZE, message = ValidationMessages.Unit_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Unit_Format_Error)
    private String unit;

    private List<Integer> parkingSlot;

    private int floor;
    private boolean isParking;
    private boolean isActive;

    public UnitMasterVO(int id, String unit) {
        this.id = id;
        this.unit = unit;
    }

    public UnitMasterVO(int id, String unit, int floor, boolean isParking, boolean isActive) {
        this.id = id;
        this.unit = unit;
        this.floor = floor;
        this.isParking = isParking;
        this.isActive = isActive;
    }

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
