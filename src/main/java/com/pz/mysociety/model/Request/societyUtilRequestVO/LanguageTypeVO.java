package com.pz.mysociety.model.Request.societyUtilRequestVO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
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
public class LanguageTypeVO extends RequestVO{

    private int id;
    private int serviceId;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Service_Name_Format_Error)
    @Size(max = VariableSize.MAX_SERVICE_NAME, message = ValidationMessages.Service_Name_Required)
    private String serviceName;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Type_Format_Error)
    @Size(max = VariableSize.MAX_TYPE_SIZE, message = ValidationMessages.Type_Length_Error)
    private String type;

    @Size(max = VariableSize.LANGUAGE_TYPE_SIZE, message = ValidationMessages.Language_Type_Length_Error)
    private String eng;

    @Size(max = VariableSize.LANGUAGE_TYPE_SIZE, message = ValidationMessages.Language_Type_Length_Error)
    private String hin;

    @Size(max = VariableSize.LANGUAGE_TYPE_SIZE, message = ValidationMessages.Language_Type_Length_Error)
    private String mar;

    public LanguageTypeVO(int serviceId, String eng) {
        this.serviceId = serviceId;
        this.eng = eng;
    }
}
