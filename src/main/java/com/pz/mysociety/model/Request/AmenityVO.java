
package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AmenityVO  extends RequestVO {


    private int id;
    private int amenityId;
    private int societyId;
    private int unitId;
    private int userId;
    private int adminId;
    private int pageNo;
    private int amenityTypeId;

    private LocalDate bookingDate;

    @Size(min = 3,max = 400, message = ValidationMessages.Amenity_Invalid_Content)
    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Description_Type_Format_Error)
    private String description;

//    @Pattern(regexp= ValidationRegEx.DateTime,message = ValidationMessages.Start_Date_Format_Error)
    private String startDate;

//    @Pattern(regexp= ValidationRegEx.DateTime,message = ValidationMessages.End_Date_Format_Error)
    //@Size(min = 3,max = 11, message = ValidationMessages.Start_Date_Length_Error)
    private String endDate;

//    @Size(min = 3,max = 11, message = ValidationMessages.Start_Time_Length_Error)
//    private String startTime;
//
//    @Size(min = 3,max = 11, message = ValidationMessages.End_Time_Length_Error)
//    private String endTime;

    private boolean isRead;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Amenity_Invalid_Status)
    private String amenityStatus;

    private String timestamp;

    @Size(max = 500, message = ValidationMessages.Amenity_Invalid_Description)
    private String content;

    private String role;

    private String attachment;

    private LocalDate fromDate;

    private  LocalDate toDate;

    private  int count;

    private Month month;

    private  int year;

    private  int resolvedCount;

    private  int pendingCount;

    private  int rejectedCount;

    private  int approvedCount;
    private String areaName;
    private int areaId;


    public boolean getIsRead(){
        return isRead;
    }
    public void setIsRead(boolean isRead){
        this.isRead = isRead;
    }
}
