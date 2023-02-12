package com.pz.mysociety.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DashBoardVO {

    //DashBoardDetail
    private int userId;
    private int societyId;
    private int areaId;
    private int unitId;
    private String name;
    private String mobileNumber;
    private String email;
    private String type;
    private String societyName;
    private String areaName;
    private String unit;
    private String address;
    private String landmark;
    private String societyBlock;
    private String city;
    private String pincode;

    private String occupancyStatus;

    private String message;
    private String userStatus;

    private String userProfile;


    public DashBoardVO(int unitId, int societyId, int areaId, String societyName, String areaName, String unit, String userStatus, String message) {
        this.societyId = societyId;
        this.areaId = areaId;
        this.unitId = unitId;
        this.societyName = societyName;
        this.areaName = areaName;
        this.unit = unit;
        this.message = message;
        this.userStatus = userStatus;
    }

    public DashBoardVO(int unitId, int societyId, int areaId, String unit, String userStatus, String message) {
        this.unitId = unitId;
        this.societyId = societyId;
        this.areaId = areaId;
        this.unit = unit;
        this.userStatus = userStatus;
        this.message = message;
    }


    public DashBoardVO(int userId, int societyId, int areaId, String type, String societyName, String areaName, String unit, String address, String landmark, String societyBlock, String city, String pincode) {
        this.userId = userId;
        this.societyId = societyId;
        this.areaId = areaId;
        this.type = type;
        this.societyName = societyName;
        this.areaName = areaName;
        this.unit = unit;
        this.address = address;
        this.landmark = landmark;
        this.societyBlock = societyBlock;
        this.city = city;
        this.pincode = pincode;
    }

    public DashBoardVO(String name, String mobileNumber, String unit, String occupancyStatus) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.unit = unit;
        this.occupancyStatus = occupancyStatus;
    }
}
