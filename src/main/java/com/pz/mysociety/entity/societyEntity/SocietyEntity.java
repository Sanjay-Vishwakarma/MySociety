package com.pz.mysociety.entity.societyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity(name = "Society")
@Table(name = "society_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = ValidationMessages.Registration_Number_Required)
    @Column(name = "registration_number")
    private String registrationNumber;

    @NotBlank(message = ValidationMessages.Society_Name_Required)
    @Column(name = "society_name")
    private String societyName;

    @NotBlank(message = ValidationMessages.Society_Name_Required)
    @Column(name = "billing_email")
    private String billingEmail;

    @NotBlank(message = ValidationMessages.Address_Required)
    @Column(name = "society_address")
    private String address;

    @Column(name = "society_landmark")
    private String landmark;

    @NotBlank(message = ValidationMessages.Society_Block_Required)
    @Column(name = "society_block")
    private String societyBlock;

    @NotBlank(message = ValidationMessages.State_Required)
    @Column(name = "state")
    private String state;

    @NotBlank(message = ValidationMessages.City_Required)
    @Column(name = "city")
    private String city;

    @NotBlank(message = ValidationMessages.Pincode_Required)
    @Column(name = "pincode")
    private String pincode;

    @Column(name = "society_status")
    private String societyStatus;

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
