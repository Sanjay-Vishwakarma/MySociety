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

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UnitDocumentMasterVO extends RequestVO {

    private int id;
    private int societyId;
    private int userId;
    private int ownerId;

    @Size(max = 50 , message = ValidationMessages.Document_Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Document_Type_Format_Error)
    private String documentType;

    private String document;

    @Size(max = 50, message = ValidationMessages.Status_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Status_Format_Error)
    private String status;

    @Pattern(regexp=ValidationRegEx.AlphaNum ,message = ValidationMessages.Remark_Format_Error)
    @Size(max = 200, message = ValidationMessages.Remark_Length_Error)
    private String remark;

    private String timestamp;
}
