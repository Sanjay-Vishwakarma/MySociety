package com.pz.mysociety.entity.householdEntity;

import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity(name = "FrequentEntry")
@Table(name = "frequent_entry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrequentEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1,message = ValidationMessages.Unit_Id_Required)
    @Column(name = "unit_id")
    private int unitId;

    @NotBlank(message = ValidationMessages.Type_Required)
    @Column(name = "type")
    private String type;

    @Column(name = "is_frequent")
    private boolean isFrequent;

    @Column(name = "days")
    private String days;

    @Column(name = "valid_days")
    private String validDays;

    @Column(name = "date")
    private String date;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "valid_for")
    private String validFor;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "company_id")
    private int companyId;

    public boolean getIsFrequent(){return isFrequent;}
    public void setIsFrequent(boolean isFrequent){this.isFrequent = isFrequent;}
}
