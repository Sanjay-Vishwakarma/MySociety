package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GuardDocumentMasterVO extends RequestVO {

    private List<String> DocumentGuardList;

    @Column(name = "id")
    private int Id;

    @Column(name = "guard_id")
    private int guardId;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "document_type_id")
    private String documentTypeId;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name="document")
    private String document;

    @Column(name="timestamp")
    private String timestamp;

}
