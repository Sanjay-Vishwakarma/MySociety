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

@Entity(name = "Guest")
@Table(name = "guest_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.Unit_Id_Required)
    @Column(name = "unit_id")
    private int unitId;

    @NotBlank(message = ValidationMessages.Name_Required)
    @Column(name = "name")
    private String name;

    @NotBlank(message = ValidationMessages.Mobile_Required)
    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "date")
    private String date;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "valid_for")
    private String validFor;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "is_frequent")
    private boolean isFrequent;


}
