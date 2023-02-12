package com.pz.mysociety.entity.helperEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "HelperDocMaster")
@Table(name = "helper_document_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HelperDocumentMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "helper_id")
    private int helperId;

    @Column(name = "document_type_id")
    private int documentTypeId;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document")
    private String document;

    @Column(name = "document_number")
    private String documentNumber;
}
