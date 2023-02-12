package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HelperMappingVO extends RequestVO {

    private int id;
    private int societyId;
    private int areaId;
    private int unitId;
    private int helperId;

    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.Review_Format_Error)
    @Size(max = 400, message = ValidationMessages.Review_Length_Error)
    private String review;

    @Range(min = 0, max = 5, message = ValidationMessages.Rating_Length_Error)
    private int rating;

    @Pattern(regexp = ValidationRegEx.AlphaNum, message = ValidationMessages.Review_Format_Error)
    @Size(max = 200, message = ValidationMessages.Remark_Length_Error)
    private String remark;

    private boolean isActive;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
