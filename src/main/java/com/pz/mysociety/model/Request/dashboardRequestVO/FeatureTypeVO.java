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
public class FeatureTypeVO extends RequestVO {

    private int id;
    private int featureId;
    private String featureName;
    private int subCategoryId;
    private String subCategoryName;
    private int dashboardId;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(max = VariableSize.MAX_DASHBOARD_SIZE, message = ValidationMessages.Description_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Description_Format_Error)
    private String description;

    private String iconUrl;

    private int priorityOrder;

    @Size(max = VariableSize.MAX_REDIRECT_SIZE, message = ValidationMessages.Redirect_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Redirect_Format_Error)
    private String redirect;

    @Size(max = VariableSize.MAX_EXTERNAL_URL_SIZE, message = ValidationMessages.External_Url_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.External_Url_Format_Error)
    private String externalUrl;

    private boolean isFavorite;
    private boolean isNew;
    private boolean isActive;

    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Action_Format_Error)
    private String action;

    public FeatureTypeVO(String name, String description, String iconUrl, String redirect, boolean isFavorite, boolean isNew) {
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.redirect = redirect;
        this.isFavorite = isFavorite;
        this.isNew = isNew;
    }

    public FeatureTypeVO(int id, String name, int priorityOrder) {
        this.id = id;
        this.name = name;
        this.priorityOrder = priorityOrder;
    }

    public FeatureTypeVO(int id, int featureId, String featureName, int subCategoryId, String subCategoryName, String name, String description, String iconUrl, String redirect, boolean isFavorite, boolean isNew, boolean isActive) {
        this.id = id;
        this.featureId = featureId;
        this.featureName = featureName;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.redirect = redirect;
        this.isFavorite = isFavorite;
        this.isNew = isNew;
        this.isActive = isActive;
    }

    public FeatureTypeVO(int id, String name, String description, String iconUrl, String redirect, boolean isNew) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.redirect = redirect;
        this.isNew = isNew;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }
}
