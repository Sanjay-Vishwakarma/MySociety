package com.pz.mysociety.entity.amenityEntity;


import com.pz.mysociety.common.constant.ValidationMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.Date;

@Entity(name = "Amenity")
@Table(name = "amenity_booking_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmenityMasterEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "unit_id")
    private int unitId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "amenity_type_id")
    private int amenityTypeId;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
//    @FutureOrPresent(message = ValidationMessages.Start_Date_Format_Error)
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

//    @Column(name = "start_time")
//    private String startTime;
//
//    @Column(name = "end_time")
//    private String endTime;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "amenity_status")
    private String amenityStatus;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name="area_id")
    private int areaId;

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
