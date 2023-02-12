package com.pz.mysociety.service.mntService;

import com.google.common.collect.Lists;
import com.pz.mysociety.common.constant.MaintenanceType;
import com.pz.mysociety.common.constant.Types;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.entity.maintenanceEntity.SocietyInvoiceEntity;
import com.pz.mysociety.repository.maintenanceRepository.SocietyInvoiceRepository;
import com.pz.mysociety.repository.maintenanceRepository.SocietyMntMappingRepository;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MntMapping {

    @Autowired
    private SocietyMntMappingRepository mntMappingRepository;

    public SocietyInvoiceEntity csvToEntity(Map<String, String> headerIndexMap, CSVRecord value) {
        SocietyInvoiceEntity societyInvoiceEntity = new SocietyInvoiceEntity();
        societyInvoiceEntity.setInvoiceStatus(Types.UPLOAD);
//        List<String> value = Arrays.asList(fileData.split(", "));

        headerIndexMap.forEach((key, val) -> {
            MntMapping.mapping(key, value.get(val), societyInvoiceEntity);
        });
        return societyInvoiceEntity;
    }

    public SocietyInvoiceEntity excelToEntity(Map<String, Integer> headerIndexMap, Row fileData) {
        SocietyInvoiceEntity societyInvoiceEntity = new SocietyInvoiceEntity();
        societyInvoiceEntity.setInvoiceStatus(Types.UPLOAD);
        ArrayList<Cell> value = Lists.newArrayList(fileData.iterator());

        headerIndexMap.forEach((key, val) -> {

            DataFormatter fmt = new DataFormatter();

            MntMapping.mapping(key, fmt.formatCellValue(value.get(val)), societyInvoiceEntity);

        });
        return societyInvoiceEntity;
    }

    public static void mapping(String key, String value, SocietyInvoiceEntity societyInvoiceEntity){

        if(Functions.nonNullString(value)) {
            switch (key) {
                case MaintenanceType.NAME:
                    societyInvoiceEntity.setName(value);
                    break;
                case MaintenanceType.MOBILE_NUMBER:
                    societyInvoiceEntity.setMobileNumber(value);
                    break;
                case MaintenanceType.AREA:
                    societyInvoiceEntity.setArea(value);
                    break;
                case MaintenanceType.UNIT:
                    societyInvoiceEntity.setUnit(value);
                    break;
                case MaintenanceType.BILL_NUMBER:
                    societyInvoiceEntity.setBillNumber(value);
                    break;
                case MaintenanceType.BILL_DATE:
                    societyInvoiceEntity.setBillDate(value);
                    break;
                case MaintenanceType.DUE_DATE:
                    societyInvoiceEntity.setDueDate(value);
                    break;
                case MaintenanceType.MONTH:
                    societyInvoiceEntity.setMonth(value);
                    break;
                case MaintenanceType.YEAR:
                    societyInvoiceEntity.setYear(value);
                    break;
                case MaintenanceType.PARKING_CHARGES:
                    societyInvoiceEntity.setParkingCharge(Double.parseDouble(value));
                    break;
                case MaintenanceType.SINKING_FUND:
                    societyInvoiceEntity.setSinkingFund(Double.parseDouble(value));
                    break;
                case MaintenanceType.REPAIR_WORK:
                    societyInvoiceEntity.setRepairWork(Double.parseDouble(value));
                    break;
                case MaintenanceType.WATER_CHARGE:
                    societyInvoiceEntity.setWaterCharge(Double.parseDouble(value));
                    break;
                case MaintenanceType.SERVICE_CHARGE:
                    societyInvoiceEntity.setServiceCharge(Double.parseDouble(value));
                    break;
                case MaintenanceType.ELECTRICITY_CHARGE:
                    societyInvoiceEntity.setElectricityCharge(Double.parseDouble(value));
                    break;
                case MaintenanceType.TOTAL_AMOUNT:
                    societyInvoiceEntity.setTotalAmount(Double.parseDouble(value));
                    break;
            }
        }
    }

    public Map<String, String> entityToType(SocietyInvoiceEntity societyInvoice) {
        Map<String, String> map = new TreeMap<>();

//        if(Functions.nonNullString(societyInvoice.getArea())){
//            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.AREA);
//            map.put(key, societyInvoice.getArea());
//        }
//
//        if(Functions.nonNullString(societyInvoice.getUnit())){
//            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.UNIT);
//            map.put(key, societyInvoice.getUnit());
//        }
//
//        if(Functions.nonNullString(societyInvoice.getBillNumber())){
//            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.BILL_NUMBER);
//            map.put(key, societyInvoice.getBillNumber());
//        }
//
//        if(Functions.nonNullString(societyInvoice.getBillDate())){
//            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.BILL_DATE);
//            map.put(key, societyInvoice.getBillDate());
//        }
//
//        if(Functions.nonNullString(societyInvoice.getDueDate())){
//            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.DUE_DATE);
//            map.put(key, societyInvoice.getDueDate());
//        }

//        if(Functions.nonNullString(societyInvoice.getMonth())){
//            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.MONTH);
//            map.put(key, societyInvoice.getMonth());
//        }

//        if(Functions.nonNullString(societyInvoice.getYear())){
//            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.YEAR);
//            map.put(key, societyInvoice.getYear());
//        }

        if(societyInvoice.getParkingCharge() != 0.0){
            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.PARKING_CHARGES);
            map.put(key, Double.toString(societyInvoice.getParkingCharge()));
        }

        if(societyInvoice.getSinkingFund() != 0.0){
            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.SINKING_FUND);
            map.put(key, Double.toString(societyInvoice.getSinkingFund()));
        }

        if(societyInvoice.getRepairWork() != 0.0){
            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.REPAIR_WORK);
            map.put(key, Double.toString(societyInvoice.getRepairWork()));
        }

        if(societyInvoice.getWaterCharge() != 0.0){
            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.WATER_CHARGE);
            map.put(key, Double.toString(societyInvoice.getWaterCharge()));
        }

        if(societyInvoice.getServiceCharge() != 0.0){
            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.SERVICE_CHARGE);
            map.put(key, Double.toString(societyInvoice.getServiceCharge()));
        }

        if(societyInvoice.getElectricityCharge() != 0.0){
            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.ELECTRICITY_CHARGE);
            map.put(key, Double.toString(societyInvoice.getElectricityCharge()));
        }

//        if(Functions.nonNullString(societyInvoice.getTotalAmount())){
//            String key = mntMappingRepository.getMntName(societyInvoice.getSocietyId(), MaintenanceType.TOTAL_AMOUNT);
//            map.put(key, societyInvoice.getTotalAmount());
//        }

        return map;
    }
}
