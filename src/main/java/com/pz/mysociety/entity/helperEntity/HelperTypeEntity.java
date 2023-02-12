package com.pz.mysociety.entity.helperEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity(name = "HelperType")
@Table(name = "helper_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HelperTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "service_type_id")
    private int serviceTypeId;

    @Column(name = "type")
    private String type;

    @Column(name = "is_full_time")
    private boolean isFullTime;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name="icon_image")
    private String iconImage;

    public boolean getIsFullTime() {
        return isFullTime;
    }

    public void setIsFullTime(boolean isFullTime) {
        this.isFullTime = isFullTime;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
