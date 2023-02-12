package com.pz.mysociety.model.Request.productServiceRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductServiceImageMasterVO extends RequestVO {

    private int id;
    private int productId;

    @Size(max = VariableSize.MAX_TYPE_SIZE, message = ValidationMessages.Type_Length_Error)
    @Pattern(regexp = ValidationRegEx.Character_Pattern, message = ValidationMessages.Type_Format_Error)
    private String type;

    private String imageUrl;

    public ProductServiceImageMasterVO(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }
}
