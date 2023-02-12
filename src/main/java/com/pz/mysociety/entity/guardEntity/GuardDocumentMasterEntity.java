package com.pz.mysociety.entity.guardEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity(name = "GuardDocumentMaster")
@Table(name = "guard_document_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GuardDocumentMasterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "guard_id")
    private int guardId;

    @Column(name = "document_type_id")
    private int documentTypeId;

    @Column(name = "document")
    private String document;

    @Column(name = "document_number")
    private String documentNumber;
}
