package com.pz.mysociety.entity.maintenanceEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "SocietyMntMapping")
@Table(name = "society_mnt_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyMntMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "mnt_type_id")
    private int mntTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "column_type")
    private String columnType;

    @Column(name = "column_number")
    private String columnNumber;

    @Column(name = "is_monthly")
    private boolean isMonthly;

    @Column(name = "calculation_logic")
    private String calculationLogic;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "timestamp")
    private String timestamp;

    public boolean getIsMonthly() {
        return isMonthly;
    }

    public void setIsMonthly(boolean isMonthly) {
        this.isMonthly = isMonthly;
    }

}
