package com.pz.mysociety.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.model.Request.CompanyMasterVO;
import com.pz.mysociety.model.Request.HelperDocumentVO;
import com.pz.mysociety.model.Request.HelperVO;
import com.pz.mysociety.model.Request.VisitorVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HelperListVO {

    private int id;
    private String areaName;
    private int unitId;
    private String unitType;
    private String unit;
    private int rating;
    private String review;
    private int helperId;
    private int passCode;
    private String helperName;
    private String helperMobile;
    private String type;
    private List<String> ownerMobileNumber;
    private CompanyMasterVO companyDetails;
    private List<HelperListVO> helperList;
    private String photo;
    private String addedBy;
    private boolean isActive;
    private String timestamp;
    private int service_type_id;
    private boolean is_full_time;
    private String freeTimeSlot;
    private String iconImage;
    private int companyId;
    private int helperTypeId;
    private String companyLogo;
    private String visitorType;
    private String phoneNumber;
    private String companyName;
    private int societyId;
    private String loginStatus;
    private String loginId;
    private String date;
    private boolean isFrequentVisitor;
    private String inTime;
    private Date outTime;
    private String helperType;
    private String approvedBy;
    private String bodyTemperature;
    private Boolean isWearingMask;
    private int count;
    private String serviceType;
    private String helperTypeIcon;
    private String serviceTypeIcon;
    private List<HelperDocumentVO> documentList;


    private List<Integer> listOfUnitId;

    public HelperListVO(int societyId, String loginId, int id, String helperName, String helperMobile, int helperTypeId, String type, String loginStatus, int companyId, String companyName, boolean isFrequentVisitor, String photo, int unitId, String inTime, String helperType) {
        this.societyId = societyId;
        this.loginId = loginId;
        this.id = id;
        this.helperName = helperName;
        this.helperMobile = helperMobile;
        this.helperTypeId = helperTypeId;
        this.type = type;
        this.loginStatus = loginStatus;
        this.companyId = companyId;
        this.companyName = companyName;
        this.isFrequentVisitor = isFrequentVisitor;
        this.photo = photo;
        this.unitId = unitId;
        this.inTime = inTime;
        this.helperType = helperType;
    }
    public HelperListVO(int id, String helperName, String helperMobile, String type, String addedBy, String photo, String timestamp, int helperTypeId,int unitId, int companyId, String companyName, String inTime, Date outTime, String approvedBy, String bodyTemperature, Boolean isWearingMask ,String loginStatus) {
        this.id = id;
        this.helperName = helperName;
        this.helperMobile = helperMobile;
        this.type = type;
        this.addedBy = addedBy;
        this.photo = photo;
        this.timestamp = timestamp;
        this.helperTypeId = helperTypeId;
        this.unitId = unitId;
        this.companyId = companyId;
        this.companyName = companyName;
        this.inTime = inTime;
        this.outTime = outTime;
        this.approvedBy = approvedBy;
        this.bodyTemperature = bodyTemperature;
        this.isWearingMask = isWearingMask;
        this.loginStatus = loginStatus;
    }
    public HelperListVO(int societyId, int id, String phoneNumber, String helperName, String companyName, String loginStatus, String visitorType, boolean isFrequentVisitor, String photo, List<Integer> listOfUnitId) {
        this.societyId = societyId;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.helperName = helperName;
        this.companyName = companyName;
        this.loginStatus = loginStatus;
        this.visitorType = visitorType;
        this.isFrequentVisitor = isFrequentVisitor;
        this.photo = photo;
        this.listOfUnitId = listOfUnitId;
    }

    @Valid
    private List<String> unitIdList;


    public HelperListVO(int societyId, int id, String phoneNumber, String helperName, String companyName, String photo, String timestamp) {
        this.societyId = societyId;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.helperName = helperName;
        this.companyName = companyName;
        this.photo = photo;
        this.timestamp = timestamp;
    }


    public HelperListVO(int id, int service_type_id, String type, String iconImage, boolean is_full_time, boolean isActive, String timestamp) {
        this.id = id;
        this.service_type_id = service_type_id;
        this.type = type;
        this.iconImage = iconImage;
        this.is_full_time = is_full_time;
        this.isActive = isActive;
        this.timestamp = timestamp;
    }

    public HelperListVO(int id, String helperName, String helperMobile, String type, String addedBy, String photo, String freeTimeSlot, String timestamp) {
        this.id = id;
        this.helperName = helperName;
        this.helperMobile = helperMobile;
        this.type = type;
        this.addedBy = addedBy;
        this.photo = photo;
        this.freeTimeSlot = freeTimeSlot;
        this.timestamp = timestamp;
    }

    public HelperListVO(String areaName, String unitType, String unit) {
        this.areaName = areaName;
        this.unitType = unitType;
        this.unit = unit;
    }

    public HelperListVO(int unitId, int rating, String review, boolean isActive) {
        this.unitId = unitId;
        this.rating = rating;
        this.review = review;
        this.isActive = isActive;
    }

    public HelperListVO(int helperId, String helperName, String helperMobile, String type, String photo, String addedBy) {
        this.helperId = helperId;
        this.helperName = helperName;
        this.helperMobile = helperMobile;
        this.type = type;
        this.photo = photo;
        this.addedBy = addedBy;
    }

    public HelperListVO(int helperId, String helperName, String helperMobile, String type, String photo, String addedBy, boolean isActive, String timestamp) {
        this.helperId = helperId;
        this.helperName = helperName;
        this.helperMobile = helperMobile;
        this.type = type;
        this.photo = photo;
        this.addedBy = addedBy;
        this.isActive = isActive;
        this.timestamp = timestamp;
    }

    public HelperListVO(int helperId, int unitId, String review, int rating, String timestamp) {
        this.helperId = helperId;
        this.unitId = unitId;
        this.review = review;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public HelperListVO(int id, int helperId) {
        this.id = id;
        this.helperId = helperId;
    }

    public HelperListVO(int id, String helperName, String helperMobile, String type, String photo) {
        this.id = id;
        this.helperName = helperName;
        this.helperMobile = helperMobile;
        this.type = type;
        this.photo = photo;

    }

    public HelperListVO(String type) {
        this.type = type;
    }

    public HelperListVO(String date, int societyId) {
        this.date = date;
        this.societyId = societyId;
    }

    public HelperListVO(int id, String helperName, String helperMobile, String type, String addedBy, String photo,String freeTimeSlot, String timestamp, int companyId,String companyName, boolean isFrequentVisitor, String loginStatus,int helperTypeId, String inTime) {
        this.id = id;
        this.helperName = helperName;
        this.helperMobile = helperMobile;
        this.type = type;
        this.addedBy = addedBy;
        this.photo = photo;
        this.freeTimeSlot = freeTimeSlot;
        this.timestamp = timestamp;
        this.companyId = companyId;
        this.companyName = companyName;
        this.isFrequentVisitor = isFrequentVisitor;
        this.loginStatus = loginStatus;
        this.helperTypeId = helperTypeId;

        this.inTime = inTime;
    }
    public HelperListVO(int id,int passCode, String helperName, String helperMobile, String type, String addedBy, String photo, String freeTimeSlot,boolean isActive, int helperTypeId,String loginStatus, boolean isFrequentVisitor,int companyId,String companyName,String helperType,String serviceType,String helperTypeIcon,String serviceTypeIcon, String timestamp) {
        this.id = id;
        this.passCode = passCode;
        this.helperName = helperName;
        this.helperMobile = helperMobile;
        this.type = type;
        this.addedBy = addedBy;
        this.photo = photo;
        this.freeTimeSlot = freeTimeSlot;
        this.isActive = isActive;
        this.helperTypeId = helperTypeId;
        this.loginStatus = loginStatus;
        this.isFrequentVisitor = isFrequentVisitor;
        this.companyId = companyId;
        this.companyName = companyName;
        this.helperType = helperType;
        this.serviceType = serviceType;
        this.helperTypeIcon = helperTypeIcon;
        this.serviceTypeIcon = serviceTypeIcon;
        this.timestamp = timestamp;
    }
    public HelperListVO(int unitId) {
        this.unitId = unitId;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public HelperListVO(String approvedBy,String loginStatus,String inTime,Date outTime) {
        this.approvedBy=approvedBy;
        this.loginStatus=loginStatus;
        this.inTime = inTime;
        this.outTime = outTime ;

    }

    public HelperListVO(int unitId,String loginId,String approvedBy,String loginStatus,String inTime,Date outTime) {
        this.unitId = unitId;
        this.loginId = loginId;
        this.approvedBy=approvedBy;
        this.loginStatus=loginStatus;
        this.inTime = inTime;
        this.outTime = outTime ;
    }

    public HelperListVO(String loginId, String helperName, String helperMobile, String photo, String approvedBy, int companyId) {
        this.loginId = loginId;
        this.helperName = helperName;
        this.helperMobile = helperMobile;
        this.photo = photo;
        this.approvedBy = approvedBy;
        this.companyId = companyId;
    }
}
