package com.pz.mysociety.entity.complainEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
@Entity(name = "ComplainHistory")
@Table(name = "complain_chat_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComplainChatRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1, message = ValidationMessages.ComplainId_Required)
    @Column(name = "complain_id")
    private int complainId;


    @Column(name = "role")
    private String role;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "admin_id")
    private int adminId;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "complain_content")
    private String content;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "timestamp")
    private String timestamp;

    public boolean getIsRead(){return isRead;}

    public void setIsRead(boolean isRead){this.isRead = isRead;}
}
