package com.pz.mysociety.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AmenityListVO {
    private int id;
    private  int userId;
    private int societyId;
    private String areaName;
    private String unit;
    private int unitId;
    private String name;
    private String userName;
    private String email;
    private String type;
    private String mobileNumber;
    private int amenityTypeId;
    private String description;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private boolean isRead;
    private String amenityStatus;
    private String duration;
    private String timestamp;


    public AmenityListVO(int id, int userId, int unitId,int societyId, int amenityTypeId, String description, String startDate, String endDate, boolean isRead, String amenityStatus, String timestamp, String type) {
        this.id = id;
        this.userId = userId;
        this.unitId = unitId;
        this.societyId = societyId;
        this.amenityTypeId = amenityTypeId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
//        this.startTime = startTime;
//        this.endTime = endTime;
        this.isRead = isRead;
        this.amenityStatus = amenityStatus;
        this.timestamp = timestamp;
        this.type = type;
    }


    public AmenityListVO(int id,int userId, int unitId, int societyId, String areaName,String userName,String email, String unit, String name, String mobileNumber, int amenityTypeId, String description, String startDate, String endDate, boolean isRead, String amenityStatus, String timestamp,String type) {
        this.id = id;
        this.userId = userId;
        this.unitId = unitId;
        this.societyId = societyId;
        this.areaName = areaName;
        this.userName = userName;
        this.email = email;
        this.unit = unit;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.amenityTypeId = amenityTypeId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
//        this.startTime = startTime;
//        this.endTime = endTime;
        this.isRead = isRead;
        this.amenityStatus = amenityStatus;
        this.timestamp = timestamp;
        this.type = type;
    }



}
