package com.pz.mysociety.model.VO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.model.Request.dashboardRequestVO.FeatureTypeVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserDashboardVO {

    private int id;
    private int labelId;
    private String labelType;
    private String label;
    private String cardName;
    private int cardSize;
    private int priorityOrder;
    private String redirect;
    private boolean isActive;

    private List<FeatureTypeVO>  featureType;

    public UserDashboardVO(int id, int labelId, String labelType, String cardName, int cardSize,  int priorityOrder, boolean isActive) {
        this.id = id;
        this.labelId = labelId;
        this.labelType = labelType;
        this.cardName = cardName;
        this.cardSize = cardSize;
        this.priorityOrder = priorityOrder;
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
