package com.pz.mysociety.entity.helperEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "HelperAttendanceMaster")
@Table(name = "helper_attendance_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HelperAttendaceMasterEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "unit_id")
    private int unitId;

    @Column(name = "area_id")
    private int areaId;

    @Column(name = "helper_id")
    private int helperId;

    @Column(name = "in_time")
    private String inTime;

    @Column(name = "out_time")
    private Date outTime;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "login_status")
    private String loginStatus;
}
