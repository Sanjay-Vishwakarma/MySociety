package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import com.pz.mysociety.entity.complainEntity.ComplainMasterEntity;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ComplainVO extends RequestVO {

    private  int id;

    private int complainId;

    private int societyId;

    private int adminId;

    //@Pattern(regexp =  ValidationRegEx.Date, message = ValidationMessages.Date_Format_Error)
    private LocalDate complainDate;

    @Pattern(regexp= ValidationRegEx.Role,message = ValidationMessages.Role_Format_Error)
    private String role;

    private int userId;

    private int unitId;

    private String isRead;

    private String content;

    @Size(max= VariableSize.COMPLAIN_TITLE_MAX_SIZE, message = ValidationMessages.Complain_Title_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Complain_Title_Format_Error)
    private String complainTitle;

    private String attachment;

    private String timestamp;

    private String complainStatus;


    @Size(max= VariableSize.COMPLAIN_CONTENT_MAX_SIZE, message = ValidationMessages.Complain_Content_Length_Error)
    private String complainContent;

    private boolean isPriority;

    public boolean getIsPriority() {
        return isPriority;
    }

    public void setIsPriority(boolean isPriority) {
        this.isPriority = isPriority;
    }

    private  int pageNo;

    private  int pageSize;

    private LocalDate fromDate;

    private  LocalDate toDate;

    private  int resolvedCount;

    private  int pendingCount;

    private  int rejectedCount;

    private Month month;

    private  int year;




}
