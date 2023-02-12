package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import com.pz.mysociety.model.RequestVO;
import com.pz.mysociety.model.VO.HelperListVO;
import com.pz.mysociety.model.VO.UnitListVO;
import com.pz.mysociety.model.VO.VisitorListVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HelperVO extends RequestVO {

    private int id;

    private int unitId;
    private String unit;

    private int societyId;
    private int helperId;
    private int passCode;
    private int areaId;
    private String areaName;
    private int helperTypeId;
    private int companyId;
    private boolean isActive;
    private String bodyTemperature;
    private String companyLogo;
    private boolean isWearingMask;
    private List<VisitorListVO> unitList1;
    private List<String> unitIdList;
    private List<UnitListVO> unitDetails;
    private int serviceTypeId;
    private int documentTypeId;
    private  boolean isMandatory;
    private String documentName;
    private String serviceType;
    private String status;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.Name_Format_Error)
    private String helperName;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp = ValidationRegEx.Number, message = ValidationMessages.Mobile_Format_Error)
    private String helperMobile;

    @Size( max = 30, message = ValidationMessages.Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.Type_Format_Error)
    private String type;

    @Size(max = 15, message = ValidationMessages.Vehicle_Number_Length_Error)
    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.Vehicle_Number_Format_Error)
    private String vehicleNumber;

    private String photo;

    private String addedBy;

    private String timestamp;

    private List<String> freeTimeSlot;

    private String documentType;

    private String document;

    private String companyName;

    private String loginId;

    private int year;

    private int month;

    private boolean isFrequentVisitor;

    private String loginStatus;

    private String date;

    private String inTime;

    private String outTime;

    @Valid
    private List<Integer> unitList;

    @Valid
    private List<HelperDocumentVO> documentList;

    public boolean getIsMandatory(){
        return isMandatory;
    }

    public void setIsMandatory(boolean isMandatory){
        this.isMandatory = isMandatory;
    }

    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }
}

