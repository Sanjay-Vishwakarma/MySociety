package com.pz.mysociety.entity.maintenanceEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "MntMaster")
@Table(name = "mnt_master_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MntMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_fee")
    private boolean isFee;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "timestamp")
    private String timestamp;

    public boolean getIsFee() {
        return isFee;
    }

    public void setIsFee(boolean isFee) {
        this.isFee = isFee;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
