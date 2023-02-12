package com.pz.mysociety.entity.guardEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity(name = "GuardHistory")
@Table(name = "guard_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GuardHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1,message = ValidationMessages.Society_Id_Required)
    @Column(name = "society_id")
    private int societyId;

    @Min(value = 1,message = ValidationMessages.Guard_Id_Required)
    @Column(name = "guard_id")
    private int guardId;

    @Column(name = "in_time")
    private String inTime;

    @Column(name = "out_time")
    private String outTime;

    @Column(name = "timestamp")
    private String timestamp;
}
