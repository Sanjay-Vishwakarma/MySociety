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

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GuestVO extends RequestVO {

    private int id;
    private int unitId;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number ,message = ValidationMessages.Mobile_Format_Error)
    private String mobileNumber;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Date_Format_Error)
    @Size(max = 30, message = ValidationMessages.Date_Length_Error)
    private String date;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Time_Format_Error)
    @Size(max = 30, message = ValidationMessages.Start_Time_Length_Error)
    private String startTime;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Time_Format_Error)
    @Size(max = 30, message = ValidationMessages.End_Time_Length_Error)
    private String validFor;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Date_Format_Error)
    @Size(max = 30, message = ValidationMessages.Start_Date_Length_Error)
    private String startDate;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Date_Format_Error)
    @Size(max = 30, message = ValidationMessages.End_Date_Length_Error)
    private String endDate;

    private boolean isFrequent;

    public boolean getIsFrequent() {
        return isFrequent;
    }

    public void setIsFrequent(boolean isFrequent) {
        this.isFrequent = isFrequent;
    }
}
