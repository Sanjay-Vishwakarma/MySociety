package com.pz.mysociety.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.model.Request.SocietyDocumentVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyUserListVO {

    private int id;
    private String name;
    private String email;
    private String mobileNumber;
    private String role;
    private String adminStatus;

    private int societyId;
    private int societyUserId;
    private String societyName;
    private String address;
    private String landmark;
    private String societyBlock;
    private String city;
    private String state;
    private String pincode;
    private String timestamp;

    private String areaName;

    private List<SocietyDocumentVO> societyDocument;

    public SocietyUserListVO(int id, int societyId, int societyUserId, String adminStatus, String societyName, String address, String landmark, String societyBlock, String state, String city, String pincode, String timestamp) {
        this.id = id;
        this.societyId = societyId;
        this.societyUserId = societyUserId;
        this.adminStatus = adminStatus;
        this.societyName = societyName;
        this.address = address;
        this.landmark = landmark;
        this.societyBlock = societyBlock;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.timestamp = timestamp;
    }

    public SocietyUserListVO(int societyId, String areaName) {
        this.societyId = societyId;
        this.areaName = areaName;
    }
}
