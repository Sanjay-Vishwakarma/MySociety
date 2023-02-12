package com.pz.mysociety.entity.complainEntity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "Complain")
@Table(name = "complain_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplainMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "unitId")
    private int unitId;

    @Column(name = "complain_title")
    private String complainTitle;

    @Column(name = "complain_content")
    private String complainContent;

    @Column(name = "is_priority")
    private boolean isPriority;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "complain_status")
    private String complainStatus;

    @Column(name = "complain_date")
    private LocalDate complainDate;

    @Column(name = "timestamp")
    private String timestamp;

    public boolean getIsPriority() {
        return isPriority;
    }

    public void setIsPriority(boolean isPriority) {
        this.isPriority = isPriority;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
