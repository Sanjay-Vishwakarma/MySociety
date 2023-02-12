package com.pz.mysociety.entity.societyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "OwnerMaster")
@Table(name = "owner_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OwnerMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.Society_Id_Required)
    @Column(name = "society_id")
    private int societyId;

    @Min(value = 1, message = ValidationMessages.User_Id_Requires)
    @Column(name = "user_id")
    private int userId;

    @Min(value = 1, message = ValidationMessages.Area_Id_Required)
    @Column(name = "area_id")
    private int areaId;

    @Min(value = 1, message = ValidationMessages.Unit_Id_Required)
    @Column(name = "unit_id")
    private int unitId;

    @NotBlank(message = ValidationMessages.Type_Required)
    @Column(name = "type")
    private String type;

    @NotBlank(message = ValidationMessages.Occupancy_Status_Required)
    @Column(name = "occupancy_status")
    private String occupancyStatus;

    @Column(name = "user_status")
    private String userStatus;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "society_role")
    private String societyRole;

    @Column(name = "is_active")
    private boolean isActive;

    public OwnerMasterEntity(int unitId, String userStatus) {
        this.unitId = unitId;
        this.userStatus = userStatus;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
