package com.pz.mysociety.entity.societyEntity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Entity(name = "Unit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "unit_master")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnitMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.Society_Id_Required)
    @Column(name = "society_id")
    private int societyId;

    @Min(value = 1, message = ValidationMessages.Area_Id_Required)
    @Column(name = "area_id")
    private int areaId;

    @Min(value = 1, message = ValidationMessages.Area_Type_Required)
    @Column(name = "area_type_id")
    private int areaTypeId;

    @Column(name = "unit")
    private String unit;

    @Column(name = "floor")
    private int floor;

    @Column(name = "is_parking")
    private boolean isParking;

    @Column(name = "is_active")
    private boolean isActive;

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
