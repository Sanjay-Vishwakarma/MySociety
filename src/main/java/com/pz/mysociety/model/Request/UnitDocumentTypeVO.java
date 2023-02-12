package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UnitDocumentTypeVO extends RequestVO{

    private int id;
    private int societyId;
    private int societyUserId;
    private int documentTypeId;

    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Resident_Type_Format_Error)
    @Size(max = 50, message = ValidationMessages.Resident_Type_Length_Error)
    private String residentType;

//    @Size(max = 200, message = ValidationMessages.Document_Type_Length_Error)
//    @Column(name = "document_type")
    private String documentType;

    private boolean isMandatory;
    private boolean isActive;
    private String timestamp;

    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Action_Format_Error)
    private String action;

    public UnitDocumentTypeVO(int id, String residentType, String documentType, boolean isMandatory, boolean isActive) {
        this.id = id;
        this.residentType = residentType;
        this.documentType = documentType;
        this.isMandatory = isMandatory;
        this.isActive = isActive;
    }

    public UnitDocumentTypeVO(String documentType) {
        this.documentType = documentType;
    }

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
