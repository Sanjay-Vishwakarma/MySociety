package com.pz.mysociety.service.mntService;

import com.google.common.collect.Lists;
import com.pz.mysociety.common.invoice.InvoicePlaceHolder;
import com.pz.mysociety.common.invoice.PdfGenerateService;
import com.pz.mysociety.common.constant.*;
import com.pz.mysociety.common.invoice.InvoiceTemplateName;
import com.pz.mysociety.common.notification.PushNotificationService;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.FileHandlingUtil;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.maintenanceEntity.MntMasterEntity;
import com.pz.mysociety.entity.maintenanceEntity.SocietyInvoiceEntity;
import com.pz.mysociety.entity.maintenanceEntity.SocietyMntMappingEntity;
import com.pz.mysociety.entity.maintenanceEntity.SocietyTemplateEntity;
import com.pz.mysociety.model.Request.SocietyRequestVO.SocietyTemplateVO;
import com.pz.mysociety.model.Request.SocietyVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.SocietyMntFileInvoiceVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.MntMasterVO;
import com.pz.mysociety.model.Request.maintenanceRequestVO.SocietyMntMappingVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.model.VO.SocietyUserListVO;
import com.pz.mysociety.model.VO.UnitListVO;
import com.pz.mysociety.repository.maintenanceRepository.MntMasterRepository;
import com.pz.mysociety.repository.maintenanceRepository.SocietyInvoiceRepository;
import com.pz.mysociety.repository.maintenanceRepository.SocietyMntMappingRepository;
import com.pz.mysociety.repository.maintenanceRepository.SocietyTemplateRepository;
import com.pz.mysociety.service.SocietyService;
import com.pz.mysociety.validation.MntMasterInputValidation;
//import com.univocity.parsers.common.record.Record;
//import com.univocity.parsers.csv.CsvParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class MntMasterService {

    @Autowired
    private MntMasterRepository mntMasterRepository;

    @Autowired
    private SocietyMntMappingRepository societyMntRepository;

    @Autowired
    private MntMasterValidationService mntMasterValidationService;

    @Autowired
    private MntMasterInputValidation mntMasterInputValidation;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SocietyInvoiceRepository invoiceRepository;

    @Autowired
    private SocietyService societyService;

    @Autowired
    private MntMapping mntMapping;

    @Autowired
    private PdfGenerateService pdfGenerateService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    private SocietyTemplateRepository templateRepository;


    public ResponseVO addMntType(MntMasterVO mntMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        mntMasterInputValidation.addMntTypeInputValidation(mntMasterVO);

        if(mntMasterVO.getAction().equalsIgnoreCase(Status.ADD)){
            MntMasterEntity mntMasterEntity = mntMasterRepository.findByName(mntMasterVO.getName());
            if(mntMasterEntity != null){
                responseVO.setFailResponse(Messages.Already_Exist);
                return responseVO;
            }

            MntMasterEntity mntMaster = modelMapper.map(mntMasterVO, MntMasterEntity.class);
            mntMasterRepository.save(mntMaster);
            responseVO.setSuccessResponse(Messages.Add_Mnt_Type_Success);
            return responseVO;
        }else if(mntMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)){

            MntMasterEntity mntMasterEntity = mntMasterRepository.getOne(mntMasterVO.getId());
            boolean isChange = false;

            if(Functions.compareValue(mntMasterEntity.getName(), mntMasterVO.getName())){
                mntMasterEntity.setName(mntMasterVO.getName());
                isChange = true;
            }

            if(mntMasterEntity.getIsFee() != mntMasterVO.getIsFee()){
                mntMasterEntity.setIsFee(mntMasterVO.getIsFee());
                isChange = true;
            }

            if(isChange){
                mntMasterRepository.save(mntMasterEntity);
                responseVO.setSuccessResponse(Messages.Update_Mnt_Type_Success);
            }else {
                responseVO.setFailResponse(Messages.No_Changes_Found);
            }
            return responseVO;
        }else {
            responseVO.setFailResponse(Messages.Invalid_Request);
        }
        return responseVO;
    }

    public ResponseVO getMntType(MntMasterVO mntMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        MntMasterEntity mntMaster = new MntMasterEntity();

        Pageable pageable = Functions.getPage(mntMasterVO.getPage(), mntMasterVO.getLimit());

        Specification<MntMasterEntity> specification = Specification
                .where(!Functions.nonNullString(mntMasterVO.getName()) ? null : SpecificationService.likeSpecification(mntMaster, EntityVariable.NAME, mntMasterVO.getName()))
                .and(Functions.nonNullString(mntMasterVO.getAction()) && mntMasterVO.getAction().equalsIgnoreCase(Types.FEE) ? SpecificationService.equalSpecification(mntMaster, EntityVariable.IS_FEE, mntMasterVO.getIsFee()) : null);

        Page<MntMasterEntity> mntMasterEntityPage = mntMasterRepository.findAll(specification, pageable);

        if(mntMasterEntityPage.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        }

        List<MntMasterVO> mntMasterVOS = modelMapperUtil.mapPage(mntMasterEntityPage, MntMasterVO.class);
        int count = (int)mntMasterRepository.count(specification);

        responseVO.setCount(count);
        responseVO.setMaintenanceTypeList(mntMasterVOS);
        responseVO.setSuccessResponse(Messages.Get_Mnt_Type_Success);

        return responseVO;
    }

    public ResponseVO addSocietyMntType(SocietyMntMappingVO societyMntMappingVO) {
        ResponseVO responseVO = new ResponseVO();
        mntMasterInputValidation.addSocietyMntTypeInputValidation(societyMntMappingVO);

        SocietyMntMappingEntity societyMntMapping = societyMntRepository.findBySocietyIdAndName(societyMntMappingVO.getSocietyId(), societyMntMappingVO.getName());
        SocietyMntMappingEntity societyMntType = societyMntRepository.findBySocietyIdAndMntTypeId(societyMntMappingVO.getSocietyId(), societyMntMappingVO.getMntTypeId());

        if(societyMntMappingVO.getAction().equalsIgnoreCase(Status.ADD)){
            mntMasterValidationService.isMntTypeIdValid(societyMntMappingVO.getMntTypeId());
            if(societyMntMapping != null || societyMntType != null) {
                responseVO.setFailResponse(Messages.Already_Exist);
                return responseVO;
            }

            SocietyMntMappingEntity societyMnt = modelMapper.map(societyMntMappingVO, SocietyMntMappingEntity.class);
            societyMntRepository.save(societyMnt);
            responseVO.setSuccessResponse(Messages.Added_Success);
            return responseVO;
        }else if(societyMntMappingVO.getAction().equalsIgnoreCase(Status.UPDATE)){

            SocietyMntMappingEntity socMntMasterEntity = societyMntRepository.getOne(societyMntMappingVO.getId());
            boolean isChange = false;

            if((societyMntMapping != null && societyMntMapping.getId() != societyMntMappingVO.getId()) ||
                    (societyMntType != null && societyMntType.getId() != societyMntMappingVO.getId())){
                responseVO.setFailResponse(Messages.Already_Exist);
                return responseVO;
            }

            if(Functions.compareValue(socMntMasterEntity.getName(), societyMntMappingVO.getName())){
                socMntMasterEntity.setName(societyMntMappingVO.getName());
                isChange = true;
            }

            if(Functions.compareValue(socMntMasterEntity.getColumnNumber(), societyMntMappingVO.getColumnNumber())){
                socMntMasterEntity.setColumnNumber(societyMntMappingVO.getColumnNumber());
                isChange = true;
            }

            if(socMntMasterEntity.getMntTypeId() != societyMntMappingVO.getMntTypeId()){
                mntMasterValidationService.isMntTypeIdValid(societyMntMappingVO.getMntTypeId());
                socMntMasterEntity.setMntTypeId(societyMntMappingVO.getMntTypeId());
                isChange = true;
            }

            if(socMntMasterEntity.getColumnType().equalsIgnoreCase(Types.FEE)) {

                if (socMntMasterEntity.getIsMonthly() != societyMntMappingVO.getIsMonthly()) {
                    socMntMasterEntity.setIsMonthly(societyMntMappingVO.getIsMonthly());
                    isChange = true;
                }

                if (Functions.compareValue(socMntMasterEntity.getCalculationLogic(), societyMntMappingVO.getCalculationLogic())) {
                    socMntMasterEntity.setCalculationLogic(societyMntMappingVO.getCalculationLogic());
                    isChange = true;
                }

                if (socMntMasterEntity.getTotalAmount() != societyMntMappingVO.getTotalAmount()) {
                    socMntMasterEntity.setTotalAmount(societyMntMappingVO.getTotalAmount());
                    isChange = true;
                }
            }

            if(isChange){
                societyMntRepository.save(socMntMasterEntity);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
            }else {
                responseVO.setFailResponse(Messages.No_Changes_Found);
            }
            return responseVO;
        }else {
            responseVO.setFailResponse(Messages.Invalid_Request);
        }
        return responseVO;

    }

    public ResponseVO getSocietyMntType(SocietyMntMappingVO societyMntMappingVO) {
        ResponseVO responseVO = new ResponseVO();
        SocietyMntMappingEntity societyMntMapping = new SocietyMntMappingEntity();
        mntMasterInputValidation.getSocietyMntTypeInputValidation(societyMntMappingVO);

        List<SocietyMntMappingEntity> a = societyMntRepository.findAll();
        System.out.println(a);

        Pageable pageable = Functions.getPage(societyMntMappingVO.getPage(), societyMntMappingVO.getLimit());

        Specification<SocietyMntMappingEntity> specification = Specification
                .where(!Functions.nonNullString(societyMntMappingVO.getName())? null : SpecificationService.likeSpecification(societyMntMapping, EntityVariable.NAME, societyMntMappingVO.getName()))
                .and(societyMntMappingVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(societyMntMapping, EntityVariable.SOCIETY_ID, societyMntMappingVO.getSocietyId()))
                .and(societyMntMappingVO.getMntTypeId() == 0 ? null : SpecificationService.equalSpecification(societyMntMapping, EntityVariable.MNT_TYPE_ID, societyMntMappingVO.getMntTypeId()))
                .and(!Functions.nonNullString(societyMntMappingVO.getColumnType()) ? null : SpecificationService.equalSpecification(societyMntMapping, EntityVariable.COLUMN_TYPE, societyMntMappingVO.getColumnType()))
                .and(!Functions.nonNullString(societyMntMappingVO.getColumnNumber()) ? null : SpecificationService.equalSpecification(societyMntMapping, EntityVariable.COLUMN_NUMBER, societyMntMappingVO.getColumnNumber()))
                .and(!Functions.nonNullString(societyMntMappingVO.getCalculationLogic()) ? null : SpecificationService.equalSpecification(societyMntMapping, EntityVariable.CALCULATION_LOGIC, societyMntMappingVO.getCalculationLogic()))
                .and(societyMntMappingVO.getTotalAmount() == 0 ? null : SpecificationService.likeSpecification(societyMntMapping, EntityVariable.TOTAL_AMOUNT, societyMntMappingVO.getTotalAmount()))
                .and(!societyMntMappingVO.getIsMonthly() ? null : SpecificationService.equalSpecification(societyMntMapping, EntityVariable.IS_MONTHLY, true));

        Page<SocietyMntMappingEntity> societyMntEntities = societyMntRepository.findAll(specification, pageable);

        if(societyMntEntities.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        }

        List<SocietyMntMappingVO> societyMntMappingVOS = new ArrayList<>();

        for (SocietyMntMappingEntity societyMntMappingEntity : societyMntEntities) {
            SocietyMntMappingVO societyMnt = modelMapper.map(societyMntMappingEntity, SocietyMntMappingVO.class);
            MntMasterEntity mntMasterEntity = mntMasterRepository.getOne(societyMnt.getMntTypeId());
            societyMnt.setMntType(mntMasterEntity.getName());
            societyMntMappingVOS.add(societyMnt);
        }

        int count = (int)societyMntRepository.count(specification);

        responseVO.setCount(count);
        responseVO.setSocietyMntList(societyMntMappingVOS);
        responseVO.setSuccessResponse(Messages.Get_Mnt_List_Success);

        return responseVO;

    }

    public ResponseVO addMntInvoice(SocietyMntFileInvoiceVO societyInvoiceVO) throws Exception  {
        ResponseVO responseVO = new ResponseVO();
        StringBuilder startAreaName = new StringBuilder();
        StringBuilder endAreaName = new StringBuilder();
//        StringBuilder month = new StringBuilder();
//        StringBuilder year = new StringBuilder();
        boolean isAdded = false;

        mntMasterInputValidation.addMntInvoiceInputValidation(societyInvoiceVO);

        SocietyVO societyVO = new SocietyVO();
        societyVO.setId(societyInvoiceVO.getSocietyId());
        ResponseVO response = societyService.getSociety(societyVO);
        String directory = response.getSociety().getSocietyName();

        String timeStamp = new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss").format(new Date());


        String fileName = timeStamp + Functions.getRandomValue()+ (societyInvoiceVO.getType().equalsIgnoreCase("xlsx") ? ".xlsx" : ".csv");

        String filePath = FileHandlingUtil.fileUploadAndDir(societyInvoiceVO.getFile(), directory, fileName, DocumentPath.UPLOAD_DIR_SOCIETY_INVOICE,null);
//            serviceTypeEntity.setIconImage(Types.SERVICE_TYPE_IMAGES+fileName);

        if(societyInvoiceVO.getType().equalsIgnoreCase("xlsx")){
                FileInputStream fileInputStream = new FileInputStream(filePath);

                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                Sheet sheet = workbook.getSheetAt(0);
                ArrayList<Row> record = Lists.newArrayList(sheet.iterator());
                Row header = sheet.getRow(0);
                ArrayList<Cell> cellList = Lists.newArrayList(header);

                Map<String, Integer> headerIndexMap = new HashMap<>();

                cellList.forEach(cell1 -> {
                    if(Functions.nonNullString(cell1.toString())) {
                        SocietyMntMappingVO mntMappingVO = societyMntRepository.getSocietyIdAndName(societyInvoiceVO.getSocietyId(), cell1.toString());
                        if(mntMappingVO != null && mntMappingVO.getMntType() != null){
                            headerIndexMap.put(mntMappingVO.getMntType(), cell1.getColumnIndex());
                        }
                    }
                });

                Set<String> checkValue = headerIndexMap.keySet();
                mntMasterValidationService.isMandatoryColumn(checkValue);


                for(int row = 1; row<record.size(); row++){
                    SocietyInvoiceEntity societyInvoiceEntity = mntMapping.excelToEntity(headerIndexMap, record.get(row));
                    if(societyInvoiceEntity.getArea() != null && societyInvoiceEntity.getUnit() != null && societyInvoiceEntity.getMonth()!=null&& societyInvoiceEntity.getYear() != null){
                        UnitListVO unitListVO = societyService.getUnitAndAreaDetail(new UnitListVO(societyInvoiceVO.getSocietyId(), societyInvoiceEntity.getArea(), societyInvoiceEntity.getUnit()));
                        if(unitListVO != null && unitListVO.getAreaId() != 0 && unitListVO.getUnitId() != 0) {
                            if(row == 1){
                                startAreaName.append(societyInvoiceEntity.getArea()).append("_").append(societyInvoiceEntity.getUnit());
//                                month.append(societyInvoiceEntity.getMonth());
//                                year.append(societyInvoiceEntity.getYear());
                            }

                            if(row == record.size() -1) endAreaName.append(societyInvoiceEntity.getArea()).append("_").append(societyInvoiceEntity.getUnit());

                            societyInvoiceEntity.setSocietyId(societyInvoiceVO.getSocietyId());
                            societyInvoiceEntity.setAreaId(unitListVO.getAreaId());
                            societyInvoiceEntity.setUnitId(unitListVO.getUnitId());
                            SocietyInvoiceEntity invoiceEntity = invoiceRepository.findByUnitIdAndMonthAndYear(societyInvoiceEntity.getUnitId(),societyInvoiceEntity.getMonth(), societyInvoiceEntity.getYear());
                            if(invoiceEntity == null){
                                isAdded = true;
                                if(!Functions.nonNullString(societyInvoiceEntity.getBillNumber())) societyInvoiceEntity.setBillNumber(societyInvoiceEntity.getYear() + "/" + societyInvoiceEntity.getMonth() + "/" + societyInvoiceEntity.getUnit());
                                invoiceRepository.save(societyInvoiceEntity);
                            }
                        }
                    }else if(societyInvoiceEntity.getArea() != null && societyInvoiceEntity.getMonth()!=null&& societyInvoiceEntity.getYear() != null){
                        UnitListVO unitListVO = societyService.getAreaDetail(new SocietyUserListVO(societyInvoiceVO.getSocietyId(), societyInvoiceEntity.getArea()));
                        if(unitListVO != null && unitListVO.getAreaId() != 0 && unitListVO.getUnitId() != 0) {
                            if(row == 1){
                                startAreaName.append(societyInvoiceEntity.getArea());
//                                month.append(societyInvoiceEntity.getMonth());
//                                year.append(societyInvoiceEntity.getYear());
                            }

                            if(row == record.size() -1) endAreaName.append(societyInvoiceEntity.getArea());


                            societyInvoiceEntity.setSocietyId(societyInvoiceVO.getSocietyId());
                            societyInvoiceEntity.setAreaId(unitListVO.getAreaId());
                            societyInvoiceEntity.setUnitId(unitListVO.getUnitId());
                            SocietyInvoiceEntity invoiceEntity = invoiceRepository.findByUnitIdAndMonthAndYear(societyInvoiceEntity.getUnitId(),societyInvoiceEntity.getMonth(), societyInvoiceEntity.getYear());
                            if(invoiceEntity == null){
                                isAdded = true;
                                invoiceRepository.save(societyInvoiceEntity);
                            }
                        }
                    }
                }

        }else {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withAllowMissingColumnNames(true)
                    .withIgnoreHeaderCase()
                    .withTrim());

            List<CSVRecord> record = csvParser.getRecords();
            List<String> headerNames = csvParser.getHeaderNames();
            Map<String, String> headerIndexMap = new HashMap<>();

            headerNames.forEach(cell1 -> {
                if(Functions.nonNullString(cell1)) {
                    SocietyMntMappingVO mntMappingVO = societyMntRepository.getSocietyIdAndName(societyInvoiceVO.getSocietyId(), cell1);
                    if(mntMappingVO != null && mntMappingVO.getMntType() != null)
                        headerIndexMap.put(mntMappingVO.getMntType(), cell1);
                }
            });

            Set<String> checkValue = headerIndexMap.keySet();
            mntMasterValidationService.isMandatoryColumn(checkValue);

            for(int row = 0; row<record.size(); row++){
                SocietyInvoiceEntity societyInvoiceEntity = mntMapping.csvToEntity(headerIndexMap, record.get(row));
                if(societyInvoiceEntity.getArea() != null && societyInvoiceEntity.getUnit() != null && societyInvoiceEntity.getMonth()!=null&& societyInvoiceEntity.getYear() != null){
                    UnitListVO unitListVO = societyService.getUnitAndAreaDetail(new UnitListVO(societyInvoiceVO.getSocietyId(), societyInvoiceEntity.getArea(), societyInvoiceEntity.getUnit()));
                    if(unitListVO != null && unitListVO.getAreaId() != 0 && unitListVO.getUnitId() != 0) {

                        if(row == 0){
                            startAreaName.append(societyInvoiceEntity.getArea()).append("_").append(societyInvoiceEntity.getUnit());
//                            month.append(societyInvoiceEntity.getMonth());
//                            year.append(societyInvoiceEntity.getYear());
                        }

                        if(row == record.size() -1) endAreaName.append(societyInvoiceEntity.getArea()).append("_").append(societyInvoiceEntity.getUnit());


                        societyInvoiceEntity.setSocietyId(societyInvoiceVO.getSocietyId());
                        societyInvoiceEntity.setAreaId(unitListVO.getAreaId());
                        societyInvoiceEntity.setUnitId(unitListVO.getUnitId());
                        SocietyInvoiceEntity invoiceEntity = invoiceRepository.findByUnitIdAndMonthAndYear(societyInvoiceEntity.getUnitId(),societyInvoiceEntity.getMonth(), societyInvoiceEntity.getYear());
                        if(invoiceEntity == null){
                            isAdded = true;
                            if(!Functions.nonNullString(societyInvoiceEntity.getBillNumber())) societyInvoiceEntity.setBillNumber(societyInvoiceEntity.getYear() + "/" + societyInvoiceEntity.getMonth() + "/" + societyInvoiceEntity.getUnit());
                            invoiceRepository.save(societyInvoiceEntity);
                        }
                    }
                }else if(societyInvoiceEntity.getArea() != null && societyInvoiceEntity.getMonth()!=null&& societyInvoiceEntity.getYear() != null){
                    UnitListVO unitListVO = societyService.getAreaDetail(new SocietyUserListVO(societyInvoiceVO.getSocietyId(), societyInvoiceEntity.getArea()));
                    if(unitListVO != null && unitListVO.getAreaId() != 0 && unitListVO.getUnitId() != 0) {

                        if(row == 0){
                            startAreaName.append(societyInvoiceEntity.getArea());
//                            month.append(societyInvoiceEntity.getMonth());
//                            year.append(societyInvoiceEntity.getYear());
                        }

                        if(row == record.size() -1) endAreaName.append(societyInvoiceEntity.getArea());

                        societyInvoiceEntity.setSocietyId(societyInvoiceVO.getSocietyId());
                        societyInvoiceEntity.setAreaId(unitListVO.getAreaId());
                        societyInvoiceEntity.setUnitId(unitListVO.getUnitId());
                        SocietyInvoiceEntity invoiceEntity = invoiceRepository.findByUnitIdAndMonthAndYear(societyInvoiceEntity.getUnitId(),societyInvoiceEntity.getMonth(), societyInvoiceEntity.getYear());
                        if(invoiceEntity == null){
                            isAdded = true;
                            invoiceRepository.save(societyInvoiceEntity);
                        }
                    }
                }
            }
        }

        String newName = startAreaName + " - " + endAreaName +"_" + timeStamp +"_" + (societyInvoiceVO.getType().equalsIgnoreCase("xlsx") ? ".xlsx" : ".csv");

        FileHandlingUtil.rename(DocumentPath.UPLOAD_DIR_SOCIETY_INVOICE,directory,filePath,newName);

        if(isAdded) {
            responseVO.setSuccessResponse(Messages.Add_Invoice_List_Success);
        } else {
            responseVO.setFailResponse(Messages.Not_Added);
        }
        return responseVO;
    }

    public ResponseVO getMntInvoice(SocietyMntFileInvoiceVO societyInvoiceVO) {
        ResponseVO responseVO = new ResponseVO();
        SocietyInvoiceEntity invoiceEntity = new SocietyInvoiceEntity();
        mntMasterInputValidation.getMntInvoiceInputValidation(societyInvoiceVO);

        Pageable pageable = Functions.getPage(societyInvoiceVO.getPage(), societyInvoiceVO.getLimit());

        Specification<SocietyInvoiceEntity> specification = Specification
                .where(societyInvoiceVO.getSocietyId() == 0 ? null : SpecificationService.equalSpecification(invoiceEntity, EntityVariable.SOCIETY_ID, societyInvoiceVO.getSocietyId()))
                .and(societyInvoiceVO.getUnitId() == 0 ? null : SpecificationService.equalSpecification(invoiceEntity, EntityVariable.UNIT_ID, societyInvoiceVO.getUnitId()))
                .and(!Functions.nonNullString(societyInvoiceVO.getArea()) ? null : SpecificationService.likeSpecification(invoiceEntity, EntityVariable.AREA, societyInvoiceVO.getArea()))
                .and(!Functions.nonNullString(societyInvoiceVO.getUnit()) ? null : SpecificationService.likeSpecification(invoiceEntity, EntityVariable.UNIT, societyInvoiceVO.getUnit()))
                .and(!Functions.nonNullString(societyInvoiceVO.getMonth()) ? null : SpecificationService.likeSpecification(invoiceEntity, EntityVariable.MONTH, societyInvoiceVO.getMonth()))
                .and(!Functions.nonNullString(societyInvoiceVO.getYear()) ? null : SpecificationService.likeSpecification(invoiceEntity, EntityVariable.YEAR, societyInvoiceVO.getYear()))
                .and(!Functions.nonNullString(societyInvoiceVO.getInvoiceStatus()) ? null : SpecificationService.equalSpecification(invoiceEntity, EntityVariable.INVOICE_STATUS, societyInvoiceVO.getInvoiceStatus()));

        Page<SocietyInvoiceEntity> societyInvoiceEntities = invoiceRepository.findAll(specification, pageable);

        if(societyInvoiceEntities.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        }

//        List<SocietyMntFileInvoiceVO> societyMntFileInvoiceVOS = modelMapperUtil.mapPage(societyInvoiceEntities, SocietyMntFileInvoiceVO.class);

        List<SocietyMntFileInvoiceVO> societyMntFileInvoiceVOS = new ArrayList<>();

        societyInvoiceEntities.forEach(invoice -> {
            SocietyMntFileInvoiceVO invoiceVO = modelMapper.map(invoice, SocietyMntFileInvoiceVO.class);
            Map<String, String> conversion = mntMapping.entityToType(invoice);
            invoiceVO.setInvoiceFee(conversion);
            societyMntFileInvoiceVOS.add(invoiceVO);
        });

        int count = (int)invoiceRepository.count(specification);

        responseVO.setCount(count);
        responseVO.setInvoiceList(societyMntFileInvoiceVOS);
        responseVO.setSuccessResponse(Messages.Get_Invoice_List_Success);
        return responseVO;
    }

    public ResponseVO generateInvoice(SocietyMntFileInvoiceVO societyInvoiceVO) {
        ResponseVO responseVO = new ResponseVO();
        mntMasterInputValidation.generateInvoiceInputValidation(societyInvoiceVO);
        boolean isUpdate = false;

        for(int id : societyInvoiceVO.getInvoiceId()){
            SocietyInvoiceEntity societyInvoice = invoiceRepository.getOne(id);

            if(societyInvoice.getInvoiceStatus().equalsIgnoreCase(Status.UPLOAD)){
                isUpdate = true;
                SocietyVO societyVO = new SocietyVO();
                societyVO.setId(societyInvoice.getSocietyId());
                SocietyTemplateEntity templateEntity = templateRepository.findBySocietyId(societyInvoice.getSocietyId());
                if(templateEntity == null) templateEntity = new SocietyTemplateEntity();

                ResponseVO response = societyService.getSociety(societyVO);
                String societyName = response.getSociety().getSocietyName();
                String dir = response.getSociety().getSocietyName() + "_" + response.getSociety().getPincode();
                societyInvoice.setInvoiceStatus(Status.SENT);


                String filepath = FileHandlingUtil.createDirectory(DocumentPath.UPLOAD_INVOICE_DIR, dir, societyInvoice.getYear(), societyInvoice.getMonth());

                String fileName = societyInvoice.getArea() + (societyInvoice.getUnit() == null ? ".pdf" : "_"+societyInvoice.getUnit() + ".pdf");

                Map<String, Object> data = new TreeMap<>();
                data.put(InvoicePlaceHolder.NAME.name(), societyInvoice.getName());
                data.put(InvoicePlaceHolder.MOBILE.name(), societyInvoice.getMobileNumber());
                data.put(InvoicePlaceHolder.HEADER.name(), Functions.nonNullString(templateEntity.getSocietyHeader())? templateEntity.getSocietyHeader() : "" );
                data.put(InvoicePlaceHolder.BILL.name(), societyInvoice.getBillNumber());
                data.put(InvoicePlaceHolder.MONTH.name(), societyInvoice.getMonth());
                data.put(InvoicePlaceHolder.DUE_DATE.name(), Functions.nonNullString(societyInvoice.getDueDate()) ? societyInvoice.getDueDate() : "-");
                data.put(InvoicePlaceHolder.EMAIL.name(), response.getSociety());
                data.put(InvoicePlaceHolder.AREA.name(), societyInvoice.getArea());
                data.put(InvoicePlaceHolder.UNIT.name(), societyInvoice.getUnit());
                data.put(InvoicePlaceHolder.AMOUNT.name(), societyInvoice.getTotalAmount());
                data.put(InvoicePlaceHolder.TERM.name(), Functions.nonNullString(templateEntity.getTermCondition())? templateEntity.getTermCondition() : "" );
                data.put(InvoicePlaceHolder.FOOTER.name(), Functions.nonNullString(templateEntity.getSocietyFooter())? templateEntity.getSocietyFooter() : "" );


                Map<String, String> value = mntMapping.entityToType(societyInvoice);
                data.put(InvoicePlaceHolder.FEE.name(), value);

                pdfGenerateService.generatePdfFile(filepath, InvoiceTemplateName.INVOICE_TEMPLATE, data,fileName);

                societyInvoice.setInvoiceUrl(Types.INVOICE+"/"+dir+"/"+societyInvoice.getYear()+"/"+societyInvoice.getMonth()+"/"+fileName);

                invoiceRepository.save(societyInvoice);
                List<String> memberToken = new ArrayList<>(societyService.getUserTokens(societyInvoice.getSocietyId(), societyInvoice.getUnitId()));

                pushNotificationService.sendActionNotificationToToken("Invoice", "New Invoice", NotificationAction.INVOICE, societyInvoice.getId(), null, memberToken);
            }
        }

        if (!isUpdate) {
            responseVO.setFailResponse(Messages.No_Changes_Found);
        } else {
            responseVO.setSuccessResponse(Messages.Update_Successfully);
        }
        return  responseVO;
    }

    public ResponseVO deleteInvoice(SocietyMntFileInvoiceVO societyInvoiceVO) {
        ResponseVO responseVO = new ResponseVO();
        mntMasterInputValidation.deleteInvoiceInputValidation(societyInvoiceVO);
        boolean isChange = false;

        for (int id:societyInvoiceVO.getInvoiceId()) {
            SocietyInvoiceEntity invoiceEntity = invoiceRepository.getOne(id);

            if(invoiceEntity.getInvoiceStatus().equalsIgnoreCase(Status.UPLOAD) || invoiceEntity.getInvoiceStatus().equalsIgnoreCase(Status.SENT)){
                if(invoiceEntity.getInvoiceStatus().equalsIgnoreCase(Status.SENT))
                    FileHandlingUtil.deleteFile(DocumentPath.SERVER_DIR, invoiceEntity.getInvoiceUrl());
                isChange = true;
                invoiceRepository.deleteById(id);
            }
        }
        if(!isChange){
            responseVO.setFailResponse(Messages.Invalid_Request);
        }else {
            responseVO.setSuccessResponse(Messages.Delete_Invoice_List_Success);
        }
        return responseVO;
    }

    public ResponseVO addInvoice(SocietyMntFileInvoiceVO societyInvoiceVO) {
        ResponseVO responseVO = new ResponseVO();
        Map<String, String> checkValue = new HashMap<>();
        mntMasterInputValidation.addInvoiceInputValidation(societyInvoiceVO);

        societyInvoiceVO.getInvoice().forEach((key, value) -> {
            if (Functions.nonNullString(key)) {
                SocietyMntMappingVO mntMappingVO = societyMntRepository.getSocietyIdAndName(societyInvoiceVO.getSocietyId(), key);
                if (mntMappingVO != null && mntMappingVO.getMntType() != null)
                    checkValue.put(mntMappingVO.getMntType(), value);
            }
        });

        if(societyInvoiceVO.getAction().equalsIgnoreCase(Status.ADD)){

            if(checkValue.size() > 0) {
//                mntMasterValidationService.isMandatoryColumn(checkValue.keySet());
                SocietyInvoiceEntity invoiceEntity = new SocietyInvoiceEntity();
                invoiceEntity.setSocietyId(societyInvoiceVO.getSocietyId());
                invoiceEntity.setUnitId(societyInvoiceVO.getUnitId());
                invoiceEntity.setInvoiceStatus(Types.UPLOAD);
                checkValue.forEach((key, value) -> MntMapping.mapping(key, value, invoiceEntity));
                invoiceEntity.setName(societyInvoiceVO.getName());
                invoiceEntity.setMobileNumber(societyInvoiceVO.getMobileNumber());
                invoiceEntity.setArea(societyInvoiceVO.getArea());
                invoiceEntity.setUnit(societyInvoiceVO.getUnit());
                invoiceEntity.setMonth(societyInvoiceVO.getMonth());
                invoiceEntity.setYear(societyInvoiceVO.getYear());
                invoiceEntity.setTotalAmount(societyInvoiceVO.getTotalAmount());

                SocietyInvoiceEntity societyInvoiceEntity = invoiceRepository.findByUnitIdAndMonthAndYear(invoiceEntity.getUnitId(),invoiceEntity.getMonth(), invoiceEntity.getYear());
                if(societyInvoiceEntity != null){
                    responseVO.setFailResponse(Messages.Already_Exist);
                    return responseVO;
                }

                invoiceRepository.save(invoiceEntity);
                responseVO.setSuccessResponse(Messages.Added_Success);
                return responseVO;
            }

        }else if(societyInvoiceVO.getAction().equalsIgnoreCase(Status.UPDATE)){
            SocietyInvoiceEntity invoiceEntity = invoiceRepository.getOne(societyInvoiceVO.getId());

            if(checkValue.size() > 0) {
                checkValue.forEach((key, value) -> MntMapping.mapping(key, value, invoiceEntity));
                invoiceEntity.setTotalAmount(societyInvoiceVO.getTotalAmount());
                invoiceRepository.save(invoiceEntity);
                responseVO.setSuccessResponse(Messages.Update_Successfully);
                return responseVO;
            }

        }
        return responseVO;
    }

    public ResponseVO deleteSocietyMntType(SocietyMntMappingVO societyMntMappingVO){
        ResponseVO responseVO=new ResponseVO();
        mntMasterInputValidation.deleteSocietyMntTypeInputValidation(societyMntMappingVO);
        societyMntRepository.deleteById(societyMntMappingVO.getId());
        responseVO.setSuccessResponse(Messages.Deleted_Successfully);
        return responseVO;
    }

    public ResponseVO addSocietyTemplate(SocietyTemplateVO societyTemplateVO) {
        ResponseVO responseVO = new ResponseVO();

        mntMasterInputValidation.addSocietyTemplateInputValidation(societyTemplateVO);

        SocietyTemplateEntity templateEntity = templateRepository.findBySocietyId(societyTemplateVO.getSocietyId());

        if(templateEntity == null){
            templateEntity = new SocietyTemplateEntity();
            templateEntity.setSocietyId(societyTemplateVO.getSocietyId());
        }


//        if(societyTemplateVO.getAction().equalsIgnoreCase(Status.ADD)){
//            templateEntity = new SocietyTemplateEntity();
//        }else if (societyTemplateVO.getAction().equalsIgnoreCase(Status.UPDATE)){
//            templateEntity = templateRepository.getOne(societyTemplateVO.getId());
//        }else {
//            responseVO.setFailResponse(Messages.Invalid_Request);
//            return responseVO;
//        }

        if (societyTemplateVO.getTemplateType().equalsIgnoreCase(Types.HEADER)) {
            templateEntity.setSocietyHeader(societyTemplateVO.getSocietyHeader());
        } else if (societyTemplateVO.getTemplateType().equalsIgnoreCase(Types.TERM)) {
            templateEntity.setTermCondition(societyTemplateVO.getTermCondition());
        } else if (societyTemplateVO.getTemplateType().equalsIgnoreCase(Types.FOOTER)) {
            templateEntity.setSocietyFooter(societyTemplateVO.getSocietyFooter());
        }else {
            responseVO.setFailResponse(Messages.Invalid_Request);
            return responseVO;
        }

        templateRepository.save(templateEntity);
        responseVO.setSuccessResponse(Messages.Update_Successfully);
        return responseVO;
    }

    public ResponseVO getSocietyTemplate(SocietyTemplateVO societyTemplateVO) {
        ResponseVO responseVO = new ResponseVO();

        mntMasterInputValidation.getSocietyTemplateInputValidation(societyTemplateVO);

        SocietyTemplateVO templateVO = templateRepository.getSocietyTemplate(societyTemplateVO.getSocietyId());

        if(templateVO == null){
            responseVO.setFailResponse(Messages.Not_Found);
            return responseVO;
        }
        responseVO.setSuccessResponse(Messages.Get_Template_Success);
        responseVO.setTemplate(templateVO);
        return responseVO;
    }

}
