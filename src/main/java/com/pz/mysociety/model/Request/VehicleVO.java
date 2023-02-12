package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import com.pz.mysociety.model.RequestVO;
import lombok.*;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class VehicleVO extends RequestVO{

    private int id;
    private int unitId;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(max = 10, message = ValidationMessages.Vehicle_Number_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum ,message = ValidationMessages.Vehicle_Number_Format_Error)
    private String vehicleNumber;

    @Pattern(regexp= ValidationRegEx.AlphaNum, message = ValidationMessages.Type_Format_Error)
    @Size(max = 30, message = ValidationMessages.Type_Length_Error)
    private String type;

    private boolean isActive;

    private String photo;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
