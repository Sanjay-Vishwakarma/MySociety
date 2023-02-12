package com.pz.mysociety.entity.societyEntity;

import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity(name = "Area")
@Table(name = "area_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaMasterEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.Society_Id_Required)
    @Column(name = "society_id")
    private int societyId;

    @Min(value = 1,message = ValidationMessages.Area_Type_Required)
    @Column(name = "area_type_id")
    private int areaTypeId;

    @NotBlank(message = ValidationMessages.Area_Name_Required)
    @Column(name = "area_name")
    private String areaName;

    @Column(name = "floor")
    private int floor;

    @Column(name = "is_active")
    private boolean isActive;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
