package com.pz.mysociety.entity.maintenanceEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "SocietyInvoice")
@Table(name = "society_invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyInvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "society_id")
    private int societyId;

    @Column(name = "area_id")
    private int areaId;

    @Column(name = "unit_id")
    private int unitId;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "area")
    private String area;

    @Column(name = "unit")
    private String unit;

    @Column(name = "bill_number")
    private String billNumber;

    @Column(name = "bill_date")
    private String billDate;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private String year;

    @Column(name = "parking_charge")
    private double parkingCharge;

    @Column(name = "sinking_fund")
    private double sinkingFund;

    @Column(name = "repair_work")
    private double repairWork;

    @Column(name = "water_charge")
    private double waterCharge;

    @Column(name = "service_charge")
    private double serviceCharge;

    @Column(name = "electricity_charge")
    private double electricityCharge;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "invoice_status")
    private String invoiceStatus;

    @Column(name = "invoice_url")
    private String invoiceUrl;

    @Column(name = "timestamp")
    private String timestamp;

}
