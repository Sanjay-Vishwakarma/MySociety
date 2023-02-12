package com.pz.mysociety.model.Request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ActivityLogVO extends RequestVO {

    private int id;

    private int unitId;

    private int userId;

    @Size(max = 400, message = ValidationMessages.Activity_type_Lentgh_Error)
    private String activityType;

    @Size(max = 500, message = ValidationMessages.Amenity_Invalid_Description)
    private String description;

    private String timestamp;

}
