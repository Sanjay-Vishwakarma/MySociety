package com.pz.mysociety.entity.productServiceEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "ServiceMaster")
@Table(name = "user_service_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserServiceMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "unit_id")
    private int unitId;

    @Column(name = "service_category_id")
    private int serviceCategoryId;

    @Column(name = "service_sub_category_id")
    private int serviceSubCategoryId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "service_price")
    private int servicePrice;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "service_day")
    private String serviceDay;

    @Column(name = "service_provide")
    private String serviceProvide;

    @Column(name = "service_range")
    private String serviceRange;

    @Column(name = "service_location")
    private String serviceLocation;

    @Column(name = "whatsapp_url")
    private String whatsappUrl;

    @Column(name = "is_call")
    private boolean isCall;

    @Column(name = "service_url")
    private String serviceUrl;

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
