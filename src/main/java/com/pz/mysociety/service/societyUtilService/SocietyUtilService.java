package com.pz.mysociety.service.societyUtilService;

import com.pz.mysociety.common.constant.EntityVariable;
import com.pz.mysociety.common.constant.Messages;
import com.pz.mysociety.common.constant.Status;
import com.pz.mysociety.common.specification.SpecificationService;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.constant.Types;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.common.util.LanguageType;
import com.pz.mysociety.common.util.ModelMapperUtil;
import com.pz.mysociety.entity.societyUtilEntity.LanguageTypeEntity;
import com.pz.mysociety.entity.societyUtilEntity.ProfessionMasterEntity;
import com.pz.mysociety.model.Request.societyUtilRequestVO.LanguageTypeVO;
import com.pz.mysociety.model.Request.societyUtilRequestVO.ProfessionUtilMasterVO;
import com.pz.mysociety.model.ResponseVO;
import com.pz.mysociety.repository.societyUtilRepository.LanguageTypeRepository;
import com.pz.mysociety.repository.societyUtilRepository.ProfessionMasterRepository;
import com.pz.mysociety.service.HelperService;
import com.pz.mysociety.validation.SocietyUtilInputValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import java.util.List;

@Transactional
@Service
public class SocietyUtilService {

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SocietyUtilInputValidator societyUtilInputValidator;

    @Autowired
    private LanguageTypeRepository languageTypeRepository;

    @Autowired
    private ProfessionMasterRepository professionMasterRepository;

    public ResponseVO addTypeLanguage(LanguageTypeVO languageTypeVO) {
        ResponseVO responseVO = new ResponseVO();
        societyUtilInputValidator.addTypeLanguageInputValidation(languageTypeVO);

        LanguageTypeEntity checkValue = languageTypeRepository.findByServiceIdAndType(languageTypeVO.getServiceId(), languageTypeVO.getType());

            if (languageTypeVO.getAction().equalsIgnoreCase(Status.ADD)) {

                if(checkValue == null){
                    checkValue = new LanguageTypeEntity();
                }

                    if(languageTypeVO.getLanguage().equalsIgnoreCase(LanguageType.HIN.name())){
                        checkValue.setHin(languageTypeVO.getServiceName());
                    }else  if(languageTypeVO.getLanguage().equalsIgnoreCase(LanguageType.MAR.name())){
                        checkValue.setMar(languageTypeVO.getServiceName());
                    }else {
                        responseVO.setFailResponse(Messages.Invalid_Request);
                        return responseVO;
                    }

                    checkValue.setServiceId(languageTypeVO.getServiceId());
                    checkValue.setType(languageTypeVO.getType());
                    checkValue.setEng(languageTypeVO.getEng());
                    languageTypeRepository.save(checkValue);
                    responseVO.setSuccessResponse(Messages.Successfully_Added);


//                else {
//                    LanguageTypeEntity languageTypeEntity = modelMapper.map(languageTypeVO, LanguageTypeEntity.class);
//                    languageTypeRepository.save(languageTypeEntity);
//                    responseVO.setSuccessResponse(Messages.Add_Language_Success);
//                }
            }else if (languageTypeVO.getAction().equalsIgnoreCase(Status.UPDATE)){

                boolean isChange = false;

                LanguageTypeEntity languageTypeEntity = languageTypeRepository.getOne(languageTypeVO.getId());

                if(Functions.compareValue(languageTypeEntity.getType(), languageTypeVO.getType())){
                    languageTypeEntity.setType(languageTypeVO.getType());
                    isChange = true;
                }

                if(Functions.compareValue(languageTypeEntity.getHin(), languageTypeVO.getHin())){
                    languageTypeEntity.setHin(languageTypeVO.getHin());
                    isChange = true;
                }

                if(Functions.compareValue(languageTypeEntity.getMar(), languageTypeVO.getMar())){
                    languageTypeEntity.setMar(languageTypeVO.getMar());
                    isChange = true;
                }

                if (isChange){
                    languageTypeRepository.save(languageTypeEntity);
                    responseVO.setSuccessResponse(Messages.Update_Language_Success);
                }else {
                    responseVO.setFailResponse(Messages.No_Changes_Found);
                }
            }else {
                responseVO.setFailResponse(Messages.Invalid_Request);
            }


        return responseVO;
    }

    public ResponseVO getTypeLanguage(LanguageTypeVO languageTypeVO) {
        ResponseVO responseVO = new ResponseVO();
        LanguageTypeEntity languageTypeEntity = new LanguageTypeEntity();

        Pageable pageable = Functions.getPage(languageTypeVO.getPage(), languageTypeVO.getLimit());

        Specification<LanguageTypeEntity> specification = Specification
                .where(languageTypeVO.getServiceId() == 0 ? null : SpecificationService.equalSpecification(languageTypeEntity, EntityVariable.SERVICE_ID, languageTypeVO.getServiceId()))
                .and(!Functions.nonNullString(languageTypeVO.getType()) ? null : SpecificationService.likeSpecification(languageTypeEntity, EntityVariable.LANGUAGE_TYPE, languageTypeVO.getType()));

        Page<LanguageTypeEntity> languageTypeEntities = languageTypeRepository.findAll(specification, pageable);

        if(languageTypeEntities.isEmpty()){
            responseVO.setFailResponse(Messages.Not_Found);
        }else{

            List<LanguageTypeVO> languageTypeVOS = modelMapperUtil.mapPage(languageTypeEntities, LanguageTypeVO.class);

            int count = (int) languageTypeRepository.count(specification);
            int page = Functions.getPagesCount(count);

            responseVO.setCount(count);
            responseVO.setPages(page);
            responseVO.setLanguageList(languageTypeVOS);
            responseVO.setSuccessResponse(Messages.Get_Language_Success);
        }
        return responseVO;
    }

    public void updateLanguage(LanguageTypeVO languageTypeVO) {

        LanguageTypeEntity languageTypeEntity = languageTypeRepository.findByServiceId(languageTypeVO.getServiceId());

        if(languageTypeEntity != null){
            languageTypeEntity.setEng(languageTypeVO.getEng());
            languageTypeRepository.save(languageTypeEntity);
        }
    }

    public ResponseVO getLanguageConversion(int serviceId, String type, String language) {
        ResponseVO responseVO = new ResponseVO();
        LanguageTypeVO languageTypeVO = new LanguageTypeVO();

        LanguageTypeEntity languageTypeEntity = languageTypeRepository.findByServiceIdAndType(serviceId, type);

        if(languageTypeEntity != null) {
            if (Functions.nonNullString(languageTypeEntity.getHin()) && language.equalsIgnoreCase(LanguageType.HIN.name()) ) {
                languageTypeVO.setServiceName(languageTypeEntity.getHin());

            }else if (Functions.nonNullString(languageTypeEntity.getMar()) && language.equalsIgnoreCase(LanguageType.MAR.name())) {
                languageTypeVO.setServiceName(languageTypeEntity.getMar());
            }else {
                languageTypeVO.setServiceName(null);
            }
            responseVO.setLanguageType(languageTypeVO);
        }else {
            responseVO = null;
        }
        return responseVO;
    }

    public ResponseVO addProfessionMaster(ProfessionUtilMasterVO professionUtilMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        societyUtilInputValidator.addProfessionMaster(professionUtilMasterVO);

        if (professionUtilMasterVO.getAction().equalsIgnoreCase(Status.ADD)) {

            ProfessionMasterEntity professionMasterEntity1 = professionMasterRepository.findByProfession(professionUtilMasterVO.getProfession());
            if (professionMasterEntity1 != null) {
                responseVO.setFailResponse(Messages.Already_Exist);
            } else {
                ProfessionMasterEntity professionMasterEntity = modelMapper.map(professionUtilMasterVO, ProfessionMasterEntity.class);
                professionMasterEntity.setIsActive(true);
                professionMasterRepository.save(professionMasterEntity);
                responseVO.setSuccessResponse(Messages.Added_Success);

            }

        } else if (professionUtilMasterVO.getAction().equalsIgnoreCase(Status.UPDATE)) {

            boolean isChange = false;

            ProfessionMasterEntity professionMasterEntity1= professionMasterRepository.findByProfession(professionUtilMasterVO.getProfession());

            ProfessionMasterEntity professionMasterEntity = professionMasterRepository.getOne(professionUtilMasterVO.getId());


            if(professionMasterEntity1 != null && professionMasterEntity1.getId() != professionUtilMasterVO.getId()){
                responseVO.setFailResponse(Messages.Already_Exist);
            }else {


                if(Functions.compareValue(professionMasterEntity.getProfession(), professionUtilMasterVO.getProfession())){
                    professionMasterEntity.setProfession(professionUtilMasterVO.getProfession());
                    isChange = true;
                }

                if(professionMasterEntity.getIsActive() != professionUtilMasterVO.getIsActive()) {
                    professionMasterEntity.setIsActive(professionUtilMasterVO.getIsActive());
                    isChange = true;
                }

                if(isChange){
                    professionMasterRepository.save(professionMasterEntity);
                    responseVO.setSuccessResponse(Messages.Update_Successfully);
                }else {
                    responseVO.setFailResponse(Messages.No_Changes_Found);
                }
            }


        } else {
            responseVO.setFailResponse(Messages.Invalid_Request);

        }

        return responseVO;
    }


    public ResponseVO getProfessionMaster(ProfessionUtilMasterVO professionUtilMasterVO) {
        ResponseVO responseVO = new ResponseVO();
        int count = 0;
        Pageable paging = Functions.getPage(professionUtilMasterVO.getPage(), professionUtilMasterVO.getLimit());
        ProfessionMasterEntity professionMasterEntity = new ProfessionMasterEntity();
        Specification<ProfessionMasterEntity> specification = Specification
                .where(!Functions.nonNullString(professionUtilMasterVO.getProfession()) ? null : SpecificationService.likeSpecification(professionMasterEntity, EntityVariable.ProfessionMaster, professionUtilMasterVO.getProfession()))
                .and(Functions.nonNullString(professionUtilMasterVO.getAction()) && professionUtilMasterVO.getAction().equalsIgnoreCase(Status.STATUS) ? SpecificationService.equalSpecification(professionMasterEntity, EntityVariable.IS_AAACTIVE, professionUtilMasterVO.getIsActive()) : null);
        Page<ProfessionMasterEntity> professionMasterEntityPage = professionMasterRepository.findAll(specification, paging);
        count = (int) professionMasterRepository.count(specification);
        if (professionMasterEntityPage.isEmpty()) {
            responseVO.setFailResponse(Messages.Not_Found);
        } else {

            List<ProfessionUtilMasterVO> activeProfession = modelMapperUtil.mapPage(professionMasterEntityPage, ProfessionUtilMasterVO.class);
            responseVO.setProfessionList(activeProfession);
            responseVO.setCount(count);
            int pages = Functions.getPagesCount(count);
            responseVO.setPages(pages);
            responseVO.setSuccessResponse(Messages.Get_Profession_List);

        }
        return responseVO;

    }}



