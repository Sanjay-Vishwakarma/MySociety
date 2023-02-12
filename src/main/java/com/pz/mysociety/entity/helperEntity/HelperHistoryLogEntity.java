package com.pz.mysociety.entity.helperEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "HelperHistoryLog")
@Table(name = "helper_history_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HelperHistoryLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "helper_id")
    private int helperId;

    @Column(name = "unit_id")
    private int unitId;

    @Column(name = "area_id")
    private int areaId;

    @Column(name = "type")
    private String type;

    @Column(name="company_id")
    private int companyId;

    @Column(name="company_name")
    private String companyName;

    @Column(name = "in_time")
    private String inTime;

    @Column(name = "out_time")
    private Date outTime;

    @Column(name = "body_temperature")
    private String bodyTemperature;

    @Column(name = "is_Wearing_mask")
    private boolean isWearingMask;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "status")
    private String status;

    public HelperHistoryLogEntity(int unitId, String status) {
        this.unitId = unitId;
        this.status = status;
    }
}
