package com.pz.mysociety.repository.userRepository;


import com.pz.mysociety.entity.userEntity.UserEntity;
import com.pz.mysociety.model.Request.UserVO;
import com.pz.mysociety.model.VO.ComplainListVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> , JpaSpecificationExecutor<UserEntity> {

//    @Query("SELECT u FROM User u WHERE u.email =?1 AND u.role = ?2")
//    UserEntity getEmailDetail(String email, String role);

//    @Query("SELECT u FROM User u WHERE u.mobileNumber =?1 AND u.role = ?2")
//    UserEntity getMobileDetail(String mobileNumber, String role);

    @Query("SELECT new com.pz.mysociety.entity.userEntity.UserEntity(u.name, u.email, u.mobileNumber, u.photo) FROM User u WHERE u.id = ?1")
    UserEntity findUserById(int id);

    @Query("SELECT new com.pz.mysociety.entity.userEntity.UserEntity(u.name, u.email, u.mobileNumber, u.photo) FROM User u WHERE u.id = ?1 AND u.role=?2")
    UserEntity findUserByIdAndRole(int id, String role);

    UserEntity findByEmailAndRole(String email, String initiatedBy);

    UserEntity findByMobileNumberAndRole(String mobileNumber, String initiatedBy);

    @Query("SELECT new com.pz.mysociety.model.VO.ComplainListVO(u.name, u.email, u.mobileNumber) FROM User u WHERE u.id = ?1")
    ComplainListVO getUserDetails(int id);

//    @Query("SELECT  u.id, u.name, u.mobileNumber  FROM User u WHERE u.id = ?1")
//    UserEntity getdUserDetails1(int id);

    UserEntity findByMobileNumber(String mobileNumber);

    UserEntity findByEmailAndPasswordAndRole(String email, String password, String initiatedBy);

    @Query(value = "SELECT u.deviceToken FROM User u WHERE u.id = ?1 AND u.isActive = ?2")
    String getUserToken(int user, boolean isActive);

    @Query(value = "SELECT u.mobileNumber FROM User u WHERE u.id = ?1 AND u.isActive = ?2")
    String getMobileNumber(int user, boolean isActive);

    @Query(value = "SELECT u.name FROM User u WHERE u.mobileNumber = ?1")
    String getOwnerName(String phoneNumber);

    @Query(value = "SELECT new com.pz.mysociety.model.Request.UserVO(u.isCall, u.userProfession, u.userSpecialization, u.userInterest, u.userAbout, u.userWebsite, u.facebookProfile, u.twitterProfile, u.linkedInProfile, u.instagramProfile) FROM User u WHERE u.id = ?1")
    UserVO getUserAboutDetail(int id);

    @Query("SELECT new com.pz.mysociety.model.Request.UserVO(u.name, u.email, u.mobileNumber, u.isCall, u.userProfession, u.userSpecialization, u.userInterest, u.userAbout, u.userWebsite, u.facebookProfile, u.twitterProfile, u.linkedInProfile, u.instagramProfile) FROM User u WHERE u.id = ?1")
    UserVO getUserName(int userId);

    UserEntity findByName(String username);

    UserEntity findByEmailAndRoleAndKeyAndToken(String userName, String role, String privateKey, String token);

    UserEntity findByMobileNumberAndRoleAndKeyAndToken(String userName, String role, String privateKey, String token);

    UserEntity findByEmailAndRoleAndKey(String subject, String role, String key);

    UserEntity findByMobileNumberAndRoleAndKey(String subject, String role, String key);
}
