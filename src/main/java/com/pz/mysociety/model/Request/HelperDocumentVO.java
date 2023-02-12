package com.pz.mysociety.model.Request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HelperDocumentVO extends RequestVO {


    private int id;
    private int societyId;
    private int helperId;
    private int helperTypeId;
    private int ServiceTypeId;
    private int documentTypeId;
    private int userId;
    private List<Integer> helperTypeIdList;


    @Size(max = 50 , message = ValidationMessages.Document_Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Document_Type_Format_Error)
    private String documentType;

    private String document;

    private String helperName;

    private String helperMobile;

    private boolean isActive;

    private boolean isMandatory;

    private String documentNumber;

    private List<HelperDocumentVO> helperDocument;

    private String mandatoryDocument;

    public HelperDocumentVO(String documentType, String document, String documentNumber) {
        this.documentType = documentType;
        this.document = document;
        this.documentNumber = documentNumber;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public  boolean getIsMandatory(){ return  isMandatory;}

    public void setIsMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

}
