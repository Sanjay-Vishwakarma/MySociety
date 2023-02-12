package com.pz.mysociety.model.Request.dashboardRequestVO;

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
public class DashboardMasterVO extends RequestVO {

    private int id;
    private int labelId;

    @Size(max = VariableSize.MAX_LABEL_SIZE, message = ValidationMessages.Label_Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Label_Type_Format_Error)
    private String labelType;

    @Size(max = VariableSize.MAX_CARD_LAYOUT_SIZE, message = ValidationMessages.Card_Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Card_Name_Format_Error)
    private String cardName;

    private int cardSize;

    private int priorityOrder;

    private boolean isActive;
    private boolean isPriority;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsPriority() {
        return isPriority;
    }

    public void setIsPriority(boolean isPriority) {
        this.isPriority = isPriority;
    }
}
