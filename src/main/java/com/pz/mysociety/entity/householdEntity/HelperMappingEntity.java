package com.pz.mysociety.entity.householdEntity;


import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity(name = "HelperMapping")
@Table(name = "helper_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelperMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.Society_Id_Required)
    @Column(name = "society_id")
    private int societyId;

    @Min(value = 1, message = ValidationMessages.Area_Id_Required)
    @Column(name = "area_id")
    private int areaId;

    @Min(value = 1, message = ValidationMessages.Unit_Id_Required)
    @Column(name = "unit_id")
    private int unitId;

    @Min(value = 1, message = ValidationMessages.Helper_Id_Required)
    @Column(name = "helper_id")
    private int helperId;

    @Column(name = "review")
    private String review;

    @Column(name = "rating")
    private int rating;

    @Column(name = "remark")
    private String remark;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "timestamp")
    private String timestamp;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
