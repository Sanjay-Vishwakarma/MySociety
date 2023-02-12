package com.pz.mysociety.model.Request.amenityRequestVO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AmenityChatRecordVO {

    private int id;
    private int amenityId;
    private int userId;
    private int adminId;
    private boolean isRead;

    @Size(max = VariableSize.MAX_ROLE_SIZE, message = ValidationMessages.Amenity_Role_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Amenity_Role_Format_Error)
    private String role;

    @Size(max = VariableSize.MAX_CONTENT_SIZE, message = ValidationMessages.Amenity_Content_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Amenity_Content_Format_Error)
    private String content;


//    @Pattern(regexp = ValidationRegEx., message = ValidationMessages.User_Website_Format_Error)
    private String attachment;

//    @Column(name = "timestamp")
    private String timestamp;

}
