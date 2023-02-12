package com.pz.mysociety.service.mntService;

import com.pz.mysociety.common.constant.MaintenanceType;
import com.pz.mysociety.common.constant.Messages;
import com.pz.mysociety.common.constant.ValidationMessages;
import com.pz.mysociety.common.exception.PZConstraintViolationException;
import com.pz.mysociety.common.exception.constraint.PZConstraint;
import com.pz.mysociety.common.exception.constraintType.PZConstraintExceptionEnum;
import com.pz.mysociety.common.util.Functions;
import com.pz.mysociety.entity.maintenanceEntity.MntMasterEntity;
import com.pz.mysociety.repository.maintenanceRepository.MntMasterRepository;
import com.pz.mysociety.repository.maintenanceRepository.SocietyMntMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class MntMasterValidationService {

    @Autowired
    private MntMasterRepository mntMasterRepository;

    public void isMntTypeIdValid(int mntTypeId) {
        MntMasterEntity mntMasterEntity = mntMasterRepository.getOne(mntTypeId);
    }

    public void isMandatoryColumn(Set<String> checkValue) {
        List<String> mandatoryColumn = Arrays.asList(MaintenanceType.NAME, MaintenanceType.MOBILE_NUMBER, MaintenanceType.AREA, MaintenanceType.MONTH, MaintenanceType.YEAR, MaintenanceType.TOTAL_AMOUNT);
        mandatoryColumn.forEach(value -> {
            if(!checkValue.contains(value)) throw new PZConstraintViolationException(value + ValidationMessages.File_Missing_Error);
        });
    }
}
