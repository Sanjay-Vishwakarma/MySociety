package com.pz.mysociety.entity.householdEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity(name = "Company")
@Table(name = "company_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CompanyMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = ValidationMessages.Name_Required)
    @Column(name = "company_name")
    private String name;

    @Min(value = 1, message = ValidationMessages.Type_Id_Required)
    @Column(name = "company_type_id")
    private int companyTypeId;

    @NotBlank(message = ValidationMessages.Company_Logo_Required)
    @Column(name = "company_logo")
    private String companyLogo;

    @Column(name = "is_active")
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
