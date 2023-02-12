package com.pz.mysociety.entity.dashboardEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "DashboardMaster")
@Table(name = "dashboard_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "label_id")
    private int labelId;

    @Column(name = "label_type")
    private String labelType;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "card_size")
    private int cardSize;

    @Column(name = "priority_order")
    private int priorityOrder;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "timestamp")
    private String timestamp;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
