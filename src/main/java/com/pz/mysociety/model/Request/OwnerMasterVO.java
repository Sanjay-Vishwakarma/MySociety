package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OwnerMasterVO  extends RequestVO{


    private int id;
    private int societyId;
    private int userId;
    private int areaId;
    private int unitId;

    @Size(max = 30, message = ValidationMessages.Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Type_Format_Error)
    private String type;

    @Size(max = 50, message = ValidationMessages.Status_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Status_Format_Error)
    private String occupancyStatus;

    @Size(max = 50, message = ValidationMessages.Status_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Status_Format_Error)
    private String userStatus;

    @Valid
    private List<UnitDocumentMasterVO> documentList;

    @Size(max = 500, message = ValidationMessages.Message_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum ,message = ValidationMessages.Message_Format_Error)
    private String message;

    private String  societyRole;

    private boolean isActive;

    private String timestamp;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
