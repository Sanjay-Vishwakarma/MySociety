package com.pz.mysociety.entity.maintenanceEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "SocietyTemplate")
@Table(name = "society_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyTemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "society_header")
    private String societyHeader;

    @Column(name = "term_condition")
    private String termCondition;

    @Column(name = "society_footer")
    private String societyFooter;

    @Column(name = "timestamp")
    private String timestamp;

}
