package com.pz.mysociety.entity.householdEntity;

import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity(name = "Vehicle")
@Table(name = "vehicle_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.Unit_Id_Required)
    @Column(name = "unit_Id")
    private int unitId;

    @NotBlank(message = ValidationMessages.Name_Required)
    @Column(name = "name")
    private String name;

    @NotBlank(message = ValidationMessages.Vehicle_Number_Required)
    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @NotBlank(message = ValidationMessages.Type_Required)
    @Column(name = "type")
    private String type;

//    @NotBlank(message = "Vehicle Photo should not be empty")
    @Column(name = "photo")
    private String photo;

    @Column(name = "is_active")
    private boolean isActive;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
