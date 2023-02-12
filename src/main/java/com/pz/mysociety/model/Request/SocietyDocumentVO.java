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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyDocumentVO extends RequestVO {

    private int id;
    private int societyId;
    private int societyUserId;
    private int documentTypeId;

//    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Society_Role_Format_Error)
//    @Size(min = 2, max = 50, message = ValidationMessages.Society_Role_Length_Error)
//    private String societyRole;

    private String documentType;

    @Size(max = 50, message = ValidationMessages.Status_Length_Error)
    @Pattern(regexp=ValidationRegEx.SafeString,message = ValidationMessages.Status_Format_Error)
    private String documentStatus;

    @Pattern(regexp=ValidationRegEx.AlphaNum ,message = ValidationMessages.Remark_Format_Error)
    @Size(max = 200, message = ValidationMessages.Remark_Length_Error)
    private String remark;

//    @Pattern(regexp = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$", message = "Invalid Document")
    private String document;


    public SocietyDocumentVO(int id, String documentType, String documentStatus, String remark, String document){
        this.id = id;
        this.documentType = documentType;
        this.documentStatus = documentStatus;
        this.remark = remark;
        this.document = document;
    }


}
