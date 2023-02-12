package com.pz.mysociety.entity.productServiceEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "ProductMaster")
@Table(name = "user_product_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductServiceMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "unit_id")
    private int unitId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "product_category_id")
    private int productCategoryId;

    @Column(name = "product_sub_category_id")
    private int productSubCategoryId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "purchase_year")
    private String purchaseYear;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private int price;

    @Column(name = "original_price")
    private int originalPrice;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "brand")
    private String brand;

    @Column(name = "product_condition")
    private String productCondition;

    @Column(name = "product_url")
    private String productUrl;

    @Column(name = "is_negotiable")
    private boolean isNegotiable;

    @Column(name = "is_call")
    private boolean isCall;

    @Column(name = "is_unit_visible")
    private boolean isUnitVisible;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "timestamp")
    private String timestamp;

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
