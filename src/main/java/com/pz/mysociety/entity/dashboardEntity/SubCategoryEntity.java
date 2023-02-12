package com.pz.mysociety.entity.dashboardEntity;

import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "SubCategory")
@Table(name = "sub_type_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "feature_id")
    private int featureId;

    @NotBlank(message = ValidationMessages.Name_Required)
    @Column(name = "name")
    private String name;

    @Column(name = "redirect")
    private String redirect;

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

}
