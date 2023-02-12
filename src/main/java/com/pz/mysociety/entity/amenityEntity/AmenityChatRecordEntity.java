package com.pz.mysociety.entity.amenityEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "AmenityHistory")
@Table(name = "amenity_chat_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AmenityChatRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "amenity_id")
    private int amenityId;

    @Column(name = "role")
    private String role;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "admin_id")
    private int adminId;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "amenity_content")
    private String content;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "timestamp")
    private String timestamp;

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
