package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import com.pz.mysociety.model.RequestVO;
import com.pz.mysociety.model.VO.VisitorListVO;
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
public class VisitorVO extends RequestVO {

    private int id;
    private int passCode;
    private int societyId;
    private int unitId;
    private String date;
    private int companyId;
    private String companyLogo;
    private List<Integer> unitIdList;
    private int visitorId;
    private String bodyTemperature;
    private boolean isWearingMask;
    private String photo;
    private String timestamp;
    private String loginStatus;
    private String approvedBy;
    private String loginId;
    private int helperTypeId;
    private boolean isActive;
    private boolean isFrequentVisitor;
    private List<VisitorListVO> unitList;

    @Size(max = 50, message = ValidationMessages.Invalid_Visitor_Type_Length)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.Invalid_Visitor_Type)
    private String visitorType;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp = ValidationRegEx.Number, message = ValidationMessages.Mobile_Format_Error)
    private String helperMobile;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.Name_Format_Error)
    private String helperName;

    @Size(max = 50, message = ValidationMessages.Company_Name_Length_Error)
    private String companyName;

    @Size(max = 10, message = ValidationMessages.Vehicle_Number_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.Vehicle_Number_Format_Error)
    private String vehicleNumber;

    @Pattern(regexp = ValidationRegEx.Status, message = ValidationMessages.Status_Format_Error)
    private String status;

    @Pattern(regexp = ValidationRegEx.Timestamp, message = ValidationMessages.In_Time_Format_Error)
    private String inTime;

    @Pattern(regexp = ValidationRegEx.Date, message = ValidationMessages.Out_Time_Format_Error)
    private String outTime;

    public boolean getIsWearingMask() {
        return isWearingMask;
    }

    public void setIsWearingMask(boolean isWearingMask) {
        this.isWearingMask = isWearingMask;
    }
}
