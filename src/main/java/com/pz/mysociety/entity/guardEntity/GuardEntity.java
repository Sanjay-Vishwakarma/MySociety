package com.pz.mysociety.entity.guardEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity(name = "Guard")
@Table(name = "guard_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GuardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1,message = ValidationMessages.Society_Id_Required)
    @Column(name = "society_id")
    private int societyId;

    @Column(name = "guard_pin")
    private int guardPin;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @NotBlank(message = ValidationMessages.Role_Required)
    @Column(name = "role")
    private String role;

    @Column(name = "otp_validate")
    private int validateOtp;

    @Column(name = "otp_duration")
    private Date otpRequestedTime;

    @Column(name = "otp_attempt")
    private int otpAttempt;

    @Column(name = "fail_attempt")
    private Date failAttempt;

    @Column(name = "photo")
    private String photo;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "timestamp")
    private String timestamp;

    public GuardEntity(int id, int societyId, String name, String email, String mobileNumber, String role, String photo) {
        this.id = id;
        this.societyId = societyId;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.photo = photo;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
