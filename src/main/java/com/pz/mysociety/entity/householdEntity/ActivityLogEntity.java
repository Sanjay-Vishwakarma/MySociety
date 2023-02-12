package com.pz.mysociety.entity.householdEntity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "acivityLog")
@Table(name = "acivity_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ActivityLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "unit_id")
    private int unitId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "activity_type")
    private String activityType;

    @Column(name = "description")
    private String description;

    @Column(name = "timestamp")
    private String timestamp;

}
