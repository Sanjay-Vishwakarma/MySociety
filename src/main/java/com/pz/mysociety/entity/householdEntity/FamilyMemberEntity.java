package com.pz.mysociety.entity.householdEntity;

import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity(name = "FamilyMember")
@Table(name = "family_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1,message = ValidationMessages.Unit_Id_Required)
    @Column(name = "unit_id")
    private int unitId;

    @Min(value = 1, message = ValidationMessages.Parent_Id_Required)
    @Column(name = "parent_id")
    private int parentId;

    @NotBlank(message = ValidationMessages.Name_Required)
    @Column(name = "name")
    private String name;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @NotBlank(message = ValidationMessages.Type_Required)
    @Column(name = "type")
    private String type;

    @Column(name = "monitor")
    private String monitor;

    @Column(name = "photo")
    private String photo;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "timestamp")
    private String timestamp;

    public FamilyMemberEntity(int id, String name, String mobileNumber,String type, String monitor, String photo) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.type = type;
        this.monitor = monitor;
        this.photo = photo;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
