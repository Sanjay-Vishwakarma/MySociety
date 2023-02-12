package com.pz.mysociety.entity.societyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.Messages;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity(name = "SocietyDocument")
@Table(name = "society_document_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyDocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.Society_Id_Required)
    @Column(name = "society_id")
    private int societyId;

    @Min(value = 1, message = ValidationMessages.Society_User_Id_Required)
    @Column(name = "society_user_id")
    private int societyUserId;

    @Min(value = 1, message = ValidationMessages.Document_Type_Id_Required)
    @Column(name = "document_type_id")
    private int documentTypeId;

    @NotBlank(message = ValidationMessages.Status_Requires)
    @Column(name = "document_status")
    private String documentStatus;

    @Column(name = "remark")
    private String remark;

    @NotBlank(message = ValidationMessages.Document_Required)
    @Column(name = "document")
    private String document;

    public SocietyDocumentEntity(int societyId, int documentTypeId) {
        this.societyId = societyId;
        this.documentTypeId = documentTypeId;
    }
}
