package com.pz.mysociety.model.Request.dashboardRequestVO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class CardMasterVO extends RequestVO {

        private String action;
        private int id;

        @Size(max = 100, message = ValidationMessages.Card_Name_Format_Error)
        @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Card_Name_Format_Error)
        private String cardName;

        private boolean isActive;
        private String timestamp;

        public boolean getIsActive() {
                return isActive;
        }

        public void setIsActive(boolean isActive) {
                this.isActive = isActive;
        }

    }







