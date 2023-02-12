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

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductServiceMasterVO extends RequestVO {

    private int id;
    private int societyId;
    private int unitId;
    private int userId;
    private int productCategoryId;
    private int productSubCategoryId;

    private String productCategoryName;
    private String productSubCategoryName;

    @Size(min = 6, max = 6, message = ValidationMessages.Pincode_Length_Error)
    @Pattern(regexp=ValidationRegEx.Number,message = ValidationMessages.Pincode_Format_Error)
    private String pincode;

    @Size(max = VariableSize.MAX_TITLE_SIZE, message = ValidationMessages.Title_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Title_Format_Error)
    private String title;

    @Size(max = VariableSize.MAX_DASHBOARD_SIZE, message = ValidationMessages.Description_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Description_Format_Error)
    private String description;

    @Size(max = VariableSize.MAX_PURCHASE_YEAR_SIZE, message = ValidationMessages.Purchase_Year_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Purchase_Year_Format_Error)
    private String purchaseYear;

    @Size(max = VariableSize.MAX_TYPE_SIZE, message = ValidationMessages.Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Type_Format_Error)
    private String type;

    private int price;

    private int originalPrice;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number,message = ValidationMessages.Mobile_Format_Error)
    private String mobileNumber;

    @Size(max = VariableSize.MAX_ADDRESS_SIZE, message = ValidationMessages.Address_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Address_Format_Error)
    private String address;

    @Size(max = VariableSize.MAX_BRAND_SIZE, message = ValidationMessages.Brand_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Brand_Format_Error)
    private String brand;

    @Size(max = VariableSize.MAX_CONDITION_SIZE, message = ValidationMessages.Condition_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Condition_Format_Error)
    private String productCondition;

//    @Size(max = VariableSize.MAX_PRODUCT_URL_SIZE, message = ValidationMessages.Product_Url_Length_Error)
//    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Product_Url_Format_Error)
    private String productUrl;

    @Valid
    List<ProductServiceImageMasterVO> productImage;

    private boolean isNegotiable;
    private boolean isCall;
    private boolean isUnitVisible;
    private boolean isActive;

    public ProductServiceMasterVO(int id, int productCategoryId, int productSubCategoryId, String title, String description, String purchaseYear, String type, int price, int originalPrice, String mobileNumber, String address, String brand, String productCondition, String productUrl, boolean isNegotiable, boolean isCall) {
        this.id = id;
        this.productCategoryId = productCategoryId;
        this.productSubCategoryId = productSubCategoryId;
        this.title = title;
        this.description = description;
        this.purchaseYear = purchaseYear;
        this.type = type;
        this.price = price;
        this.originalPrice = originalPrice;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.brand = brand;
        this.productCondition = productCondition;
        this.productUrl = productUrl;
        this.isNegotiable = isNegotiable;
        this.isCall = isCall;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsNegotiable() {
        return isNegotiable;
    }

    public void setIsNegotiable(boolean isNegotiable) {
        this.isNegotiable = isNegotiable;
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
