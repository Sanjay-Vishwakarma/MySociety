package com.pz.mysociety.entity.societyUtilEntity;

import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity(name = "LanguageType")
@Table(name = "type_language_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "type")
    private String type;

    @Column(name = "eng")
    private String eng;

    @Column(name = "hin")
    private String hin;

    @Column(name = "mar")
    private String mar;

    @Column(name = "timestamp")
    private String timestamp;

}
