package com.pz.mysociety.model.Request.maintenanceRequestVO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyMntMappingVO extends RequestVO {

    private int id;
    private int societyId;
    private int mntTypeId;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(max = VariableSize.MAX_COLUM_TYPE, message = ValidationMessages.Column_Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Column_Type_Format_Error)
    private String columnType;

    @Size(max = VariableSize.MAX_COLUM_NUMBER, message = ValidationMessages.Column_Number_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Column_Number_Format_Error)
    private String columnNumber;

    private boolean isMonthly;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Calculation_Logic_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Calculation_Logic_Format_Error)
    private String calculationLogic;

    private double totalAmount;
    private String mntType;

    public SocietyMntMappingVO(String mntType) {
        this.mntType = mntType;
    }

    public boolean getIsMonthly() {
        return isMonthly;
    }

    public void setIsMonthly(boolean isMonthly) {
        this.isMonthly = isMonthly;
    }

}
