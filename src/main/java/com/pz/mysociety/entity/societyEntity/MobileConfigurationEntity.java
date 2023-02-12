package com.pz.mysociety.entity.societyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "MobileProvider")
@Table(name = "mobile_configuration")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MobileConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "provider_key")
    private String providerKey;

    @Column(name = "is_active")
    private boolean isActive;

    public MobileConfigurationEntity(int id, String providerName, boolean isActive) {
        this.id = id;
        this.providerName = providerName;
        this.isActive = isActive;
    }

    public boolean getIsActive(){return isActive;}

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }
}
