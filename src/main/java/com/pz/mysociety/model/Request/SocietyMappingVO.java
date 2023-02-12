package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyMappingVO extends RequestVO {

//    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Society_Role_Format_Error)
//    @Size(min = 2, max = 50, message = ValidationMessages.Society_Role_Length_Error)
//    private String societyRole;

    @Valid
    private SocietyVO society;

    @Valid
    private UserVO user;

    @Valid
    private UnitMasterVO unit;

    @Valid
    private OwnerMasterVO owner;

    @Valid
    private GuestVO guestVO;
}
