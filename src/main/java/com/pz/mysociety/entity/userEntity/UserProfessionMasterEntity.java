package com.pz.mysociety.entity.userEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "ProfessionMaster")
@Table(name = "profession_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserProfessionMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "unit_id")
    private int unitId;

    @Column(name = "profession")
    private String profession;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_number")
    private String mobileNumber;
}
