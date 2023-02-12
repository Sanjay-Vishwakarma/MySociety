package com.pz.mysociety.model.Request.SocietyRequestVO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class ParkingSlotVO extends RequestVO {
    private int id;
    private int societyId;
    private int unitId;
    private int areaId;
    private String name;
    private boolean isOccupied;
    private boolean isVisitor;
    private int unitIdMapping;
    private boolean isActive;
    private String timestamp;

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public boolean getIsVisitor() {
        return isVisitor;
    }

    public void setIsVisitor(boolean isVisitor) {
        this.isVisitor = isVisitor;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }



}
