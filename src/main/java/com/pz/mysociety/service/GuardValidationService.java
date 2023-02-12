package com.pz.mysociety.service;

import com.pz.mysociety.entity.guardEntity.GuardEntity;
import com.pz.mysociety.repository.guardRepository.GuardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GuardValidationService {

    @Value( "${otp.duration}")
    private long otpDuration;

    @Value( "${login.attempt.duration}")
    private long loginAttemptDuration;

    @Autowired
    private GuardRepository guardRepository;


    public boolean isLoginValid(GuardEntity guardEntity){

        if(guardEntity.getFailAttempt() != null) {
            long attemptTimeInMills = guardEntity.getFailAttempt().getTime();
            if (attemptTimeInMills + loginAttemptDuration > System.currentTimeMillis()) {
                return false;
            }else {
                guardEntity.setFailAttempt(null);
                guardRepository.save(guardEntity);
            }
        }
        return true;
    }

    public boolean isOtpValid(GuardEntity guardEntity){

        if(guardEntity.getValidateOtp() == 0){
            return false;
        }

        long otpTimeInMills = guardEntity.getOtpRequestedTime().getTime();
        if(otpTimeInMills + otpDuration < System.currentTimeMillis()){
            return false;
        }


        return true;
    }
}
