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
public class VisitorHistoryLogVO extends RequestVO {

    private int id;
    private int helperId;
    private int societyId;
    private int unitId;
    private int visitorId;
    private int visitorLogId;
    private String phoneNumber;
    private String approvedBy;
    private String date;
    private int companyId;
    private int activityId;
    private List<Integer> unitIdList;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.In_Time_Format_Error)
    private String inTime;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Out_Time_Format_Error)
    private String outTime;

    private String action;

    @Size(max = 20, message = ValidationMessages.Body_Temperature_Length_Error)
    private String bodyTemperature;

    private boolean isWearingMask;

    @Size(max = 15, message = ValidationMessages.Invalid_VehicleNumber)
    @Pattern(regexp= ValidationRegEx.AlphaNum ,message = ValidationMessages.Vehicle_Number_Format_Error)
    private String vehicleNumber;

    private String status;

    private int passCode;

    private String visitorType;

    public boolean getIsWearingMask() {
        return isWearingMask;
    }

    public void setIsWearingMask(boolean isWearingMask) {
        this.isWearingMask = isWearingMask;
    }
}
