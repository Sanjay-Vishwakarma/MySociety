package com.pz.mysociety.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HelperDocumentListVO{

    private int id;
    private int societyId;
    private String mandatoryDocument;
    private boolean isMandatory;
    private int serviceTypeId;
    private int helperTypeId;
    private boolean isActive;
    private int documentTypeId;
    private String documentName;
    private String serviceType;
    private String helperType;
    private String iconImage;

    public HelperDocumentListVO(int societyId, String mandatoryDocument, boolean isMandatory) {
        this.societyId = societyId;
        this.mandatoryDocument = mandatoryDocument;
        this.isMandatory = isMandatory;
    }
    public HelperDocumentListVO(int id,int societyId, int helperTypeId, int serviceTypeId, int documentTypeId, boolean isActive, boolean isMandatory, String helperType, String iconImage,String serviceType) {
        this.id = id;
        this.societyId = societyId;
        this.helperTypeId = helperTypeId;
        this.serviceTypeId = serviceTypeId;
        this.documentTypeId = documentTypeId;
        this.isActive = isActive;
        this.isMandatory = isMandatory;
        this.helperType = helperType;
        this.iconImage = iconImage;
        this.serviceType = serviceType;

    }
}