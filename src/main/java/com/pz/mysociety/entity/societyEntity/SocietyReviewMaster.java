package com.pz.mysociety.entity.societyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

//@Entity(name = "Unit")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "unit_master")
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocietyReviewMaster {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name = "society_id")
//    private int societyId;
//
//    @Column(name = "area_id")
//    private int areaId;
//
//    @Column(name = "owner_id")
//    private int ownerId;
//
//    @Column(name = "description")
//    private String description;
//
//    @Column(name = "is_name_display")
//    private boolean isNameDisplay;
//
//    @Column(name = "is_construction")
//    private boolean isConstruction;
//
//    @Column(name = "is_amenity_quality")
//    private boolean isAmenityQuality;
//
//    @Column(name = "is_safe")
//    private boolean isSafe;
//
//    @Column(name = "is_prominent")
//    private boolean isProminent;
//
//    @Column(name = "is_commute")
//    private boolean isCommute;
//
//    @Column(name = "is_noise")
//    private boolean isNoise;
//
//    @Column(name = "is_eventful")
//    private boolean isEventful;
//
//    @Column(name = "is_clean")
//    private boolean isClean;
//
//    @Column(name = "is_friction")
//    private boolean isFriction;
//
//    @Column(name = "resident_type")
//    private String residentType;
//
//    @Column(name = "timestamp")
//    private String timestamp;

}
