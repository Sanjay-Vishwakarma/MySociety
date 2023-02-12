package com.pz.mysociety.entity.helperEntity;

import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "ServiceType")
@Table(name = "service_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = ValidationMessages.Type_Required)
    @Column(name = "type")
    private String type;

    @Column(name = "is_society_user")
    private boolean isSocietyUser;

    @Column(name = "is_user")
    private boolean isUser;

    @Column(name = "is_guard")
    private boolean isGuard;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name="icon_image")
    private String iconImage;

    @Column(name="is_attendance")
    private boolean isAttendance;

    @Column(name="is_visitor")
    private boolean isVisitor;

    public boolean getIsSocietyUser() {
        return isSocietyUser;
    }

    public void setIsSocietyUser(boolean isSocietyUser) {
        this.isSocietyUser = isSocietyUser;
    }

    public boolean getIsUser() {
        return isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public boolean getIsGuard() {
        return isGuard;
    }

    public void setIsGuard(boolean isGuard) {
        this.isGuard = isGuard;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsAttendance() {
        return isAttendance;
    }

    public void setIsAttendance(boolean isAttendance) {
        this.isAttendance = isAttendance;
    }

    public boolean getIsVisitor() {
        return isVisitor;
    }

    public void setIsVisitor(boolean isVisitor) {
        this.isVisitor = isVisitor;
    }
}
