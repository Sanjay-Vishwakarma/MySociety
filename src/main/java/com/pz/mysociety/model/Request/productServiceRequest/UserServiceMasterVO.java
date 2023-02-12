package com.pz.mysociety.model.Request.productServiceRequest;

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
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserServiceMasterVO extends RequestVO {

    private int id;
    private int societyId;
    private int userId;
    private int unitId;
    private int serviceCategoryId;
    private int serviceSubCategoryId;

    private String serviceCategoryName;
    private String serviceSubCategoryName;

    @Size(max = VariableSize.MAX_TITLE_SIZE, message = ValidationMessages.Title_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Title_Format_Error)
    private String title;

    @Size(max = VariableSize.MAX_DASHBOARD_SIZE, message = ValidationMessages.Description_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Description_Format_Error)
    private String description;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number,message = ValidationMessages.Mobile_Format_Error)
    private String mobileNumber;


    private int servicePrice;

    @Size(max = VariableSize.MAX_SERVICE_TYPE_SIZE, message = ValidationMessages.Service_Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Service_Type_Format_Error)
    private String serviceType;

    @Size(max = VariableSize.MAX_SERVICE_DAY_SIZE, message = ValidationMessages.Service_Day_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Service_Day_Format_Error)
    private String serviceDay;

    @Size(max = VariableSize.MAX_SERVICE_PROVIDE_SIZE, message = ValidationMessages.Service_Provide_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Service_Provide_Format_Error)
    private String serviceProvide;

    @Size(max = VariableSize.MAX_SERVICE_RANGE_SIZE, message = ValidationMessages.Service_Range_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Service_Range_Format_Error)
    private String serviceRange;

    @Size(max = VariableSize.MAX_SERVICE_LOCATION_SIZE, message = ValidationMessages.Service_Location_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Service_Location_Format_Error)
    private String serviceLocation;

//    @Size(max = VariableSize.MAX_SERVICE_WHATAPP_URL_SIZE, message = ValidationMessages.Service_Whatsapp_Url_Length_Error)
//    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Service_Whatsapp_Url_Format_Error)
    private String whatsappUrl;

    private boolean isCall;

//    @Size(max = VariableSize.MAX_SERVICE_URL_SIZE, message = ValidationMessages.Service_Url_Length_Error)
//    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Service_Url_Format_Error)
    private String serviceUrl;

    private boolean isUnitVisible;
    private boolean isActive;

    @Valid
    List<ProductServiceImageMasterVO> productImage;

    private String timestamp;

    public UserServiceMasterVO(int id, int serviceCategoryId, int serviceSubCategoryId, String title, String description, String mobileNumber, int servicePrice, String serviceType, String serviceDay, String serviceProvide, String serviceRange, String serviceLocation, String whatsappUrl, boolean isCall, String serviceUrl, boolean isUnitVisible) {
        this.id = id;
        this.serviceCategoryId = serviceCategoryId;
        this.serviceSubCategoryId = serviceSubCategoryId;
        this.title = title;
        this.description = description;
        this.mobileNumber = mobileNumber;
        this.servicePrice = servicePrice;
        this.serviceType = serviceType;
        this.serviceDay = serviceDay;
        this.serviceProvide = serviceProvide;
        this.serviceRange = serviceRange;
        this.serviceLocation = serviceLocation;
        this.whatsappUrl = whatsappUrl;
        this.isCall = isCall;
        this.serviceUrl = serviceUrl;
        this.isUnitVisible = isUnitVisible;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsCall() {
        return isCall;
    }

    public void setIsCall(boolean isCall) {
        this.isCall = isCall;
    }

    public boolean getIsUnitVisible() {
        return isUnitVisible;
    }

    public void setIsUnitVisible(boolean isUnitVisible) {
        this.isUnitVisible = isUnitVisible;
    }

}
