package com.pz.mysociety.model.Request.maintenanceRequestVO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.constant.ValidationRegEx;
import com.pz.mysociety.common.constant.VariableSize;
import com.pz.mysociety.model.RequestVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SocietyMntFileInvoiceVO extends RequestVO {

    private int id;
    private int societyId;
    private int areaId;
    private int unitId;
    private ArrayList<Integer> invoiceId;
    private HashMap<String, String> invoice;

    @Pattern(regexp= ValidationRegEx.Invoice_File_Type,message = ValidationMessages.Type_Format_Error)
    private String type;
    private String file;

    @Size(max = VariableSize.NAME_SIZE, message = ValidationMessages.Name_Length_Error)
    @Pattern(regexp = ValidationRegEx.SafeString, message = ValidationMessages.Name_Format_Error)
    private String name;

    @Size(min = 10, max = 10, message = ValidationMessages.Mobile_Length_Error)
    @Pattern(regexp= ValidationRegEx.Number,message = ValidationMessages.Mobile_Format_Error)
    private String mobileNumber;

    @Size(max = VariableSize.MAX_AREA_SIZE, message = ValidationMessages.Area_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Area_Name_Format_Error)
    private String area;

    @Size(max = VariableSize.MAX_UNIT_SIZE, message = ValidationMessages.Unit_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Unit_Format_Error)
    private String unit;

    @Size(max = VariableSize.MAX_BILL_NUMBER, message = ValidationMessages.Bill_Number_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Bill_Number_Format_Error)
    private String billNumber;

    @Size(max = VariableSize.MAX_BILL_DATE, message = ValidationMessages.Bill_Date_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Bill_Date_Format_Error)
    private String billDate;

    @Size(max = VariableSize.MAX_DUE_DATE, message = ValidationMessages.Due_Date_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Due_Date_Format_Error)
    private String dueDate;

    @Size(max = VariableSize.MAX_MONTH, message = ValidationMessages.Month_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Month_Length_Error)
    private String month;

    @Size(max = VariableSize.MAX_YEAR, message = ValidationMessages.Year_Length_Error)
    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Year_Format_Error)
    private String year;

//    @Size(max = VariableSize.MAX_PARKING_CHARGE, message = ValidationMessages.Parking_Charge_Length_Error)
//    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Parking_Charge_Format_Error)
    private double parkingCharge;

//    @Size(max = VariableSize.MAX_SINKING_FUND, message = ValidationMessages.Sinking_Fund_Length_Error)
//    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Sinking_Fund_Format_Error)
    private double sinkingFund;

//    @Size(max = VariableSize.MAX_REPAIR_WORK, message = ValidationMessages.Repair_Work_Length_Error)
//    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Repair_Work_Format_Error)
    private double repairWork;

//    @Size(max = VariableSize.MAX_WATER_CHARGE, message = ValidationMessages.Water_Charge_Length_Error)
//    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Water_Charge_Format_Error)
    private double waterCharge;

//    @Size(max = VariableSize.MAX_SERVICE_CHARGE, message = ValidationMessages.Service_Charge_Format_Error)
//    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Service_Charge_Length_Error)
    private double serviceCharge;

//    @Size(max = VariableSize.MAX_ELECTRICITY_CHARGE, message = ValidationMessages.Electricity_Charge_Length_Error)
//    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Electricity_Charge_Format_Error)
    private double electricityCharge;

//    @Size(max = VariableSize.MAX_TOTAL_AMOUNT, message = ValidationMessages.Total_Amount_Length_Error)
//    @Pattern(regexp= ValidationRegEx.AlphaNum,message = ValidationMessages.Total_Amount_Format_Error)
    private double totalAmount;

    private Map<String, String> invoiceFee;
    private String invoiceStatus;
    private String invoiceUrl;
    private String timestamp;
}
