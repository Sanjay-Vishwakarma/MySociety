package com.pz.mysociety.entity.helperEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Helper")
@Table(name = "helper_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "pass_code")
    private int passCode;

    @Column(name = "helper_name")
    private String helperName;

    @Column(name = "helper_mobile")
    private String helperMobile;

    @Column(name = "helper_type_id")
    private int helperTypeId;

    @Column(name = "photo")
    private String photo;

    @Column(name="added_by")
    private String addedBy;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name="free_time_slot")
    private String freeTimeSlot;

    @Column(name="login_status")
    private String loginStatus;

    @Column(name="login_id")
    private String loginId;

    @Column(name="is_active")
    private boolean isActive;

    @Column(name="is_frequent_visitor")
    private boolean isFrequentVisitor;

    @Column(name="type")
    private String type;

    @Column(name="company_id")
    private int companyId;

    @Column(name="company_name")
    private String companyName;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {this.isActive = isActive;}

    public boolean getIsFrequentVisitor() {
        return isFrequentVisitor;
    }

    public void setIsFrequentVisitor(boolean isFrequentVisitor) {this.isFrequentVisitor = isFrequentVisitor;}
}
