package com.pz.mysociety.model.Request.SocietyRequestVO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
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
public class SocietyTemplateVO extends RequestVO {

    private int id;
    private int societyId;

    @Pattern(regexp= ValidationRegEx.Template_Type,message = ValidationMessages.Type_Format_Error)
    private String templateType;

//    @Size(max = VariableSize.MAX_HTML_TEMPLATE_SIZE, message = ValidationMessages.Html_Template_Length_Error)
//    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Html_Template_Format_Error)
    private String societyHeader;

//    @Size(max = VariableSize.MAX_HTML_TEMPLATE_SIZE, message = ValidationMessages.Html_Template_Length_Error)
//    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Html_Template_Format_Error)
    private String termCondition;

//    @Size(max = VariableSize.MAX_HTML_TEMPLATE_SIZE, message = ValidationMessages.Html_Template_Length_Error)
//    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Html_Template_Format_Error)
    private String societyFooter;

    private String timestamp;

    public SocietyTemplateVO(int id, String societyHeader, String termCondition, String societyFooter) {
        this.id = id;
        this.societyHeader = societyHeader;
        this.termCondition = termCondition;
        this.societyFooter = societyFooter;
    }
}
