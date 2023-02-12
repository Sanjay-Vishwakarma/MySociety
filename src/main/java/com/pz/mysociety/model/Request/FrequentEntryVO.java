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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class FrequentEntryVO extends RequestVO {

    private int id;
    private int unitId;

    @Pattern(regexp= ValidationRegEx.AlphaNum, message = ValidationMessages.Type_Format_Error)
    @Size(max = 30, message = ValidationMessages.Type_Length_Error)
    private String type;

    private boolean isFrequent;

    @Pattern(regexp= ValidationRegEx.SafeString, message = ValidationMessages.Date_Format_Error)
    @Size(max = 100, message = ValidationMessages.Days_Length_Error)
    private String days;

    @Pattern(regexp= ValidationRegEx.Date, message = ValidationMessages.Valid_Days_Format_Error)
    @Size(max = 100, message = ValidationMessages.Valid_Days_Length_Error)
    private String validDays;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Date_Format_Error)
    @Size(max = 30, message = ValidationMessages.Date_Length_Error)
    private String date;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Time_Format_Error)
    @Size(max = 30, message = ValidationMessages.Start_Time_Length_Error)
    private String startTime;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Time_Format_Error)
    @Size(max = 30, message = ValidationMessages.End_Time_Length_Error)
    private String validFor;

    @Size(max = 10, message = ValidationMessages.Vehicle_Number_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number ,message = ValidationMessages.Vehicle_Number_Format_Error)
    private String vehicleNumber;

    private int companyId;

    public boolean getIsFrequent(){return isFrequent;}
    public void setIsFrequent(boolean isFrequent){this.isFrequent = isFrequent;}
}
