package com.pz.mysociety.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ActivityListVO {

    private String name;
    private String mobileNumber;
    private String date;
    private String inTime;
    private Date outTime;
    private String approvedBY;
    private String activityType;
    private String description;
    private String type;
    private int activityId;
    private int id;
    private int societyId;
    private String photo;
    private String timestamp;
    private int helperTypeId;
    private int companyId;
    private String loginStatus;
    private String companyName;
    private String companyLogo;
    private String approvedBy;

    public ActivityListVO(int activityId, int id, String type,int societyId, String date, String inTime, Date outTime, String description, String timestamp, String name,int helperTypeId,  String mobileNumber, String photo, int companyId, String loginStatus) {
        this.activityId = activityId;
        this.id = id;
        this.type = type;
        this.societyId = societyId;
        this.date = date;
        this.inTime = inTime;
        this.outTime = outTime;
        this.description = description;
        this.timestamp = timestamp;
        this.name = name;
        this.helperTypeId = helperTypeId;
        this.mobileNumber = mobileNumber;
        this.photo = photo;
        this.companyId = companyId;
        this.loginStatus = loginStatus;
    }

    public ActivityListVO( String date, String inTime, Date outTime, String description, String timestamp) {
        this.date = date;
        this.inTime = inTime;
        this.outTime = outTime;
        this.description = description;
        this.timestamp = timestamp;
    }

}
