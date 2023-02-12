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
import javax.validation.constraints.Size;

@Entity(name = "UnitDocumentType")
@Table(name = "unit_document_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UnitDocumentTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.Unit_Id_Required)
    @Column(name = "society_id")
    private int societyId;

    @Min(value = 1, message = ValidationMessages.Society_User_Id_Required)
    @Column(name = "society_user_id")
    private int societyUserId;

    @NotBlank(message = ValidationMessages.Resident_Type_Required)
    @Column(name = "resident_type")
    private String residentType;

    @Min(value = 1, message = ValidationMessages.Document_Type_Id_Required)
    @Column(name = "document_type_id")
    private int documentTypeId;

    @Column(name = "is_mandatory")
    private boolean isMandatory;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "timestamp")
    private String timestamp;

    public boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
