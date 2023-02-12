package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.Messages;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.model.RequestVO;
import com.pz.mysociety.model.VO.UnitListVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyNoticeVO extends RequestVO {

    private int id;
    private int societyId;
    private List<Integer> unitId;
    private String noticeFor;

    @Size(max = 200, message = ValidationMessages.Invalid_Noice_Title)
    private String noticeTitle;

    @Size(min = 5, max = 10000, message = ValidationMessages.Notice_Content_length_Error)
    private String noticeContent;

    @Size(max = 50, message =ValidationMessages.Notice_Type_length_Error)
    private String noticeType;

    private String attachment;

  //  @Size(max = 15, message = ValidationMessages.Start_Date_Length_Error)
    private LocalDate startDate;

   // @Size(max = 15, message = ValidationMessages.End_Time_Length_Error)
    private LocalDate endDate;

    private boolean isActive;

    private List<UnitListVO> unitList;

    private String timestamp;

    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }

    public SocietyNoticeVO(int id, String noticeTitle, String noticeContent, String noticeType, LocalDate startDate, LocalDate endDate, String attachment, String timestamp) {
        this.id = id;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeType = noticeType;
        this.attachment = attachment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timestamp = timestamp;
    }
}
