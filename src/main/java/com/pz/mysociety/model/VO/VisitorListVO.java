package com.pz.mysociety.model.VO;

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
public class VisitorListVO extends RequestVO {

    private int id;
    private int passCode;
    private int societyId;
    private int unitId;
    private List<Integer> unitIdList;
    private int visitorId;
    private String visitorType;
    private String phoneNumber;
    private String name;
    private String companyName;
    private String companyLogo;
    private String vehicleNumber;
    private String status;
    private String inTime;
    private String outTime;
    private String bodyTemperature;
    private boolean isWearingMask;
    private String photo;
    private String timestamp;
    private String loginStatus;
    private int loginId;
    private boolean isActive;
    private boolean isFrequentVisitor;
    private String unit;
    private List<String> ownerMobileNumber;



    public VisitorListVO(int unitId) {
        this.unitId = unitId;
    }

    public VisitorListVO(int societyId, int id, String phoneNumber, String name, String companyName, String loginStatus, String visitorType, boolean isFrequentVisitor, String photo, int unitId, String companyLogo) {
        this.societyId = societyId;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.companyName = companyName;
        this.loginStatus = loginStatus;
        this.visitorType = visitorType;
        this.isFrequentVisitor = isFrequentVisitor;
        this.photo = photo;
        this.unitId = unitId;
        this.companyLogo = companyLogo;
    }

    public boolean getIsWearingMask() {
        return isWearingMask;
    }

    public void setIsWearingMask(boolean isWearingMask) {
        this.isWearingMask = isWearingMask;
    }
}
