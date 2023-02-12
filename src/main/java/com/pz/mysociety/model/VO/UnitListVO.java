package com.pz.mysociety.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UnitListVO {

    private int societyId;
    private int areaId;
    private int floor;
    private int unitId;
    private String areaName;
    private String areaType;
    private int areaTypeId;
    private String unit;
    private boolean isParking;
    private boolean isActive;

    public UnitListVO(int societyId, String areaName, String unit) {
        this.societyId = societyId;
        this.areaName = areaName;
        this.unit = unit;
    }

    public UnitListVO(int areaId, String areaName, int areaTypeId, int floor, boolean isActive) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.areaTypeId = areaTypeId;
        this.floor = floor;
        this.isActive = isActive;
    }

    public UnitListVO(int areaId, String areaName, String areaType, int floor, boolean isActive) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.areaType = areaType;
        this.floor = floor;
        this.isActive = isActive;
    }

    public UnitListVO(int areaId, int areaTypeId, String areaType, int unitId, String areaName, int floor, String unit, boolean isParking, boolean isActive) {
        this.areaId = areaId;
        this.areaTypeId = areaTypeId;
        this.areaType = areaType;
        this.unitId = unitId;
        this.areaName = areaName;
        this.floor = floor;
        this.unit = unit;
        this.isParking = isParking;
        this.isActive = isActive;
    }

    public UnitListVO(String areaName, String unit) {
        this.areaName = areaName;
        this.unit = unit;
    }

    public UnitListVO(int areaId, String areaName, int floor) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.floor = floor;
    }

    public UnitListVO(int areaId, int unitId, String areaName, String unit, int floor) {
        this.areaId = areaId;
        this.unitId = unitId;
        this.areaName = areaName;
        this.unit = unit;
        this.floor = floor;
    }

    public UnitListVO(int unitId, String unit) {
        this.unitId = unitId;
        this.unit = unit;
    }

    public UnitListVO(int areaId, int unitId) {
        this.areaId = areaId;
        this.unitId = unitId;
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
