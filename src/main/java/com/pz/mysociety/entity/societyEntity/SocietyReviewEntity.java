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

@Entity(name = "societyReviewEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "society_review")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocietyReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "area_id")
    private int areaId;

    @Column(name = "owner_id")
    private int ownerId;

    @Column(name = "description")
    private String description;

    @Column(name = "is_name_display")
    private boolean isNameDisplay;

    @Column(name = "construction")
    private String construction;

    @Column(name = "amenity_quality")
    private String amenityQuality;

    @Column(name = "safe")
    private String safe;

    @Column(name = "prominent")
    private String prominent;

    @Column(name = "commute")
    private String commute;

    @Column(name = "noise")
    private String noise;

    @Column(name = "event_full")
    private String eventFull;

    @Column(name = "clean")
    private String clean;

    @Column(name = "friction")
    private String friction;

    @Column(name = "resident_type")
    private String residentType;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name= "rating")
    private int rating;

 public boolean getNameDisplay()
 {return isNameDisplay;}

 public void setNameDisplay(boolean isNameDisplay)
 {this.isNameDisplay= isNameDisplay;}


}
