package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HouseholdVO extends RequestVO{

    private int unitId;
    private String  householdType;

    private int societyId;

    private String name;

    private String mobileNumber;

    private String date;

    private String inTime;

    private String outTime;

    private String allowedBy;

    private String activityType;

    private int companyId;

    private String photo;

    private String timestamp;


}
