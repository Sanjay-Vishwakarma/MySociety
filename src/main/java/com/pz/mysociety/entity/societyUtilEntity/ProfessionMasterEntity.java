package com.pz.mysociety.entity.societyUtilEntity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

    @Entity(name = "ProfessionMasterEntity")
    @Table(name = "profession_master")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public class ProfessionMasterEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "profession")
        private String profession;

        @Column(name = "is_active")
        private boolean isActive;

        @Column(name = "timestamp")
        private String timestamp;

        public boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {this.isActive = isActive;}





    }

