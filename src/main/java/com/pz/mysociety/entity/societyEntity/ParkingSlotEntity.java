package com.pz.mysociety.entity.societyEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="ParkingSlotEntity")
@Table(name="parking_slot_master")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ParkingSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name= "unit_id")
    private int unitId;

    @Column(name= "area_id")
    private int areaId;

    @Column (name= "name")
    private String name;

    @Column(name= "is_occupied")
    private boolean isOccupied;

    @Column(name="is_visitor")
    private boolean isVisitor;

    @Column (name= "is_active")
    private boolean isActive;

    @Column(name = "unitId_mapping")
    private int unitIdMapping;

    @Column (name= "timestamp")
    private String timestamp;

//    public boolean getisOccupied()
//    {return getisOccupied();}
//
//    public void setisOccupied(boolean isOccupied)
//    {this.isOccupied= isOccupied;}
//
//    public boolean getisVisitor()
//    {return getisVisitor();}
//
//    public void setisVisitor(boolean isVisitor)
//    {this.isVisitor= isVisitor;}
//
//    public boolean getisActive()
//    {return getisActive();}
//
//    public void setisActive(boolean isActive)
//    {this.isActive= isActive;}


    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public boolean getIsVisitor() {
        return isVisitor;
    }

    public void setIsVisitor(boolean isVisitor) {
        this.isVisitor = isVisitor;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
