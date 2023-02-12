package com.pz.mysociety.entity.societyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "emergency_number")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EmergencyNumberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.Society_Id_Required)
    @Column(name = "society_id")
    private int societyId;

    @NotBlank(message = ValidationMessages.Name_Required)
    @Column(name = "name")
    private String name;

    @NotBlank(message = ValidationMessages.Mobile_Required)
    @Column(name = "mobile_number")
    private String mobileNumber;

    @NotBlank(message = ValidationMessages.Type_Required)
    @Column(name = "type")
    private String type;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "is_active")
    private boolean isActive;

    public boolean getIsActive(){return isActive;}

    public void setIsActive(boolean isActive){this.isActive = isActive;}
}
