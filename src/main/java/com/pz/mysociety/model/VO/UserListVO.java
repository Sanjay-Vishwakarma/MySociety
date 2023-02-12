package com.pz.mysociety.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.model.Request.UnitDocumentMasterVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserListVO {

    private int userId;
    private int applicationId;
    private String societyName;
    private String areaName;
    private String unit;
    private String name;
    private String mobileNumber;
    private String email;
    private String type;
    private String occupancyStatus;
    private String userStatus;
    private String message;
    private String societyRole;
    private String timestamp;
    private List<UnitDocumentMasterVO> documentList;
    private String userName;
    private int floor;
    private boolean isCall;

    public UserListVO(int userId, int applicationId, String societyName, String areaName, String unit, String type, String occupancyStatus, String userStatus, String message, String societyRole, String timestamp) {
        this.userId = userId;
        this.applicationId = applicationId;
        this.societyName = societyName;
        this.areaName = areaName;
        this.unit = unit;
        this.type = type;
        this.occupancyStatus = occupancyStatus;
        this.userStatus = userStatus;
        this.message = message;
        this.societyRole = societyRole;
        this.timestamp = timestamp;
    }

    public UserListVO(String societyName, String areaName, String unit, String name, String mobileNumber, String email, String type, String occupancyStatus, String userStatus) {
        this.societyName = societyName;
        this.areaName = areaName;
        this.unit = unit;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.type = type;
        this.occupancyStatus = occupancyStatus;
        this.userStatus = userStatus;
    }

    public UserListVO(String userName, String mobileNumber) {
        this.userName = userName;
        this.mobileNumber = mobileNumber;
    }

    public UserListVO(int userId, String areaName, String unit, int floor, String societyRole) {
        this.userId = userId;
        this.areaName = areaName;
        this.unit = unit;
        this.floor = floor;
        this.societyRole = societyRole;
    }

    public UserListVO(int userId, String type, String unit, int floor) {
        this.userId = userId;
        this.type = type;
        this.unit = unit;
        this.floor = floor;
    }

    public UserListVO(String societyName, String areaName, String unit) {
        this.societyName = societyName;
        this.areaName = areaName;
        this.unit = unit;
    }

    public boolean getIsCall() {
        return isCall;
    }

    public void setIsCall(boolean isCall) {
        this.isCall = isCall;
    }
}
