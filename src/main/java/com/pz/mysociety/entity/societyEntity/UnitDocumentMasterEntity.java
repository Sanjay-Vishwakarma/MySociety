package com.pz.mysociety.entity.societyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.UserStatus;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity(name = "UnitDocumentMaster")
@Table(name = "unit_document_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UnitDocumentMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.Society_Id_Required)
    @Column(name = "society_id")
    private int societyId;

    @Min(value = 1, message = ValidationMessages.User_Id_Requires)
    @Column(name = "user_id")
    private int userId;

    @Min(value = 1, message = ValidationMessages.Owner_Id_Required)
    @Column(name = "owner_id")
    private int ownerId;

    @NotBlank(message = ValidationMessages.Document_Type_Required)
    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document")
    private String document;

    @NotBlank(message = ValidationMessages.Status_Requires)
    @Column(name = "status")
    private String status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "timestamp")
    private String timestamp;

}
