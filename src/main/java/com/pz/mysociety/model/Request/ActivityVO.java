package com.pz.mysociety.model.Request;

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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ActivityVO extends RequestVO{


    private int id;
    private int societyId;
    private int unitId;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number,message = ValidationMessages.Mobile_Format_Error)
    private String mobileNumber;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Date_Format_Error)
    @Size(max = 30, message = ValidationMessages.Date_Length_Error)
    private String date;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.In_Time_Format_Error)
    @Size(max = 30, message = ValidationMessages.In_Time_Length_Error)
    private String inTime;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Out_Time_Format_Error)
    @Size(max = 30, message = ValidationMessages.Out_Time_Length_Error)
    private String outTime;

    @Size(max = 50, message = ValidationMessages.Allowed_By_Required)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Allowed_By_Format_Error)
    private String allowedBy;

    @Size(max = 30, message = ValidationMessages.Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Type_Format_Error)
    private String activityType;

    private String companyName;

    private String photo;

    private String timestamp;


}
