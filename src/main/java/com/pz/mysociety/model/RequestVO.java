package com.pz.mysociety.model;

import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
public class RequestVO extends InputVO{


    private String initiatedBy;
    private int page;
    private int limit;
    private String action;
    private String filter;
    private String language;
}
