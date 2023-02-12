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
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HelperHistoryLogVO extends RequestVO {
    private int id;
    private String loginId;

    private int helperTypeId;

    private int societyId;

    private int unitId;

    private int areaId;

    private int helperId;

    private String type;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.In_Time_Format_Error)
    private String inTime;

    //@Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Out_Time_Format_Error)
    private String outTime;

//    private String action;

    private String bodyTemperature;

    private boolean isWearingMask;

    private String vehicleNumber;

    private String date;

    private int activityId;

    private int companyId;
    private String companyName;

    private String approvedBy;

    private String mobileNumber;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String format;

    private int count;

    private Month month;

    private int year;

    private List<Integer> unitIdList;

    public HelperHistoryLogVO(int id, int societyId, String loginId) {
        this.id = id;
        this.societyId = societyId;
        this.loginId = loginId;
    }

    public boolean getIsWearingMask() {
        return isWearingMask;
    }

    public void setIsWearingMask(boolean isWearingMask) {
        this.isWearingMask = isWearingMask;
    }
}
