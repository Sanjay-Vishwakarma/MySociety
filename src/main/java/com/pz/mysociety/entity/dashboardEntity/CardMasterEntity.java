package com.pz.mysociety.entity.dashboardEntity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



    @Entity(name = "CardMasterEntity")
    @Table(name = "card_layout_master")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class CardMasterEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "card_name")
        private String cardName;

        @Column(name = "is_active")
        private boolean isActive;

        @Column(name = "timestamp")
        private String timestamp;

        public boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {this.isActive = isActive;}





}
