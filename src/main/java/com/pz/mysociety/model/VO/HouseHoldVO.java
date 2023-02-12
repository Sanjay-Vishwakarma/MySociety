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
public class HouseHoldVO {

    private int id;

    //VehicleResponse
    private String vehicleName;
    private String vehicleNumber;
    private String vehicleType;
    private boolean isActive;
    private String photo;

    //GuestResponse
    private String guestName;
    private String guestNumber;


    public HouseHoldVO(int id, String vehicleName, String vehicleNumber, String vehicleType, boolean isActive, String photo) {
        this.id = id;
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.isActive = isActive;
        this.photo = photo;
    }

    public HouseHoldVO(int id, String guestName, String guestNumber) {
        this.id = id;
        this.guestName = guestName;
        this.guestNumber = guestNumber;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
