package com.pz.mysociety.entity.dashboardEntity;

import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "FeatureType")
@Table(name = "feature_type_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "feature_id")
    private int featureId;

    @Column(name = "sub_category_id")
    private int subCategoryId;

    @Column(name = "dashboard_id")
    private int dashboardId;

    @NotBlank(message = ValidationMessages.Name_Required)
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "redirect")
    private String redirect;

    @Column(name = "external_url")
    private String externalUrl;

    @Column(name = "priority_order")
    private int priorityOrder;

    @Column(name = "is_favorite")
    private boolean isFavorite;

    @Column(name = "is_new")
    private boolean isNew;

    @Column(name = "is_Active")
    private boolean isActive;

    @Column(name = "timestamp")
    private String timestamp;

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
