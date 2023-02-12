package com.pz.mysociety.model.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ServiceTypeVO extends RequestVO{

    private int id;

    @Pattern(regexp= ValidationRegEx.SafeString,message = ValidationMessages.Type_Format_Error)
    @Size(max = 30, message = ValidationMessages.Type_Length_Error)
    private String type;


    private String languageType;

    private boolean isSocietyUser;
    private boolean isUser;
    private boolean isGuard;
    private boolean isActive;
    private boolean isAttendance;
    private boolean isVisitor;

    private String iconImage;

    private List<HelperTypeVO> helperType;

    public boolean getIsSocietyUser() {
        return isSocietyUser;
    }

    public void setIsSocietyUser(boolean isSocietyUser) {
        this.isSocietyUser = isSocietyUser;
    }

    public boolean getIsUser() {
        return isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public boolean getIsGuard() {
        return isGuard;
    }

    public void setIsGuard(boolean isGuard) {
        this.isGuard = isGuard;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsAttendance() {
        return isAttendance;
    }

    public void setIsAttendance(boolean isAttendance) {
        this.isAttendance = isAttendance;
    }

    public boolean getIsVisitor() {
        return isVisitor;
    }

    public void setIsVisitor(boolean isVisitor) {
        this.isVisitor = isVisitor;
    }
}
