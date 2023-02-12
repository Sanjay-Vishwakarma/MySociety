package com.pz.mysociety.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ComplainListVO {

    private  int id;
    private  int userId;
    private  int unitId;
    private String complainTitle;
    private String complainContent;
    private String attachment;
    private String complainStatus;
    private boolean isPriority;
    private String timestamp;
    private boolean isRead;
    private String complainDate;

    private String areaName;
    private String unit;
    private String userName;
    private String email;
    private String mobileNumber;

    private UnitListVO unitListVO;

    public ComplainListVO(int id, int userId,int unitId, String complainTitle, String complainContent, String attachment, String complainStatus, boolean isPriority, String timestamp, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.unitId = unitId;
        this.complainTitle = complainTitle;
        this.complainContent = complainContent;
        this.attachment = attachment;
        this.complainStatus = complainStatus;
        this.isPriority = isPriority;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }
    public ComplainListVO(String areaName, String unit) {
        this.areaName = areaName;
        this.unit = unit;
    }



    public ComplainListVO(int userId,String userName,  String mobileNumber) {
        this.userId = userId;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
    }

    public ComplainListVO(String userName, String email, String mobileNumber) {
        this.userName = userName;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }
}
