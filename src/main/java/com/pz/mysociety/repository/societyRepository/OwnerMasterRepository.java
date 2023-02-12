package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.OwnerMasterEntity;
import com.pz.mysociety.model.VO.DashBoardVO;
import com.pz.mysociety.model.VO.UserListVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface OwnerMasterRepository extends JpaRepository<OwnerMasterEntity, Integer>, JpaSpecificationExecutor<OwnerMasterEntity> {

    @Query(value = "SELECT new com.pz.mysociety.model.VO.DashBoardVO(u.id, u.societyId, u.areaId, s.societyName, a.areaName, u.unit, om.userStatus, om.message) FROM OwnerMaster om JOIN Unit u ON om.unitId = u.id JOIN Society s ON om.societyId = s.id JOIN Area a ON om.areaId = a.id WHERE om.userId = ?1 ORDER BY om.userStatus DESC")
    List<DashBoardVO> getUserRequest(int userId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.DashBoardVO(u.id, u.societyId, u.areaId, s.societyName, a.areaName, u.unit, om.userStatus, om.message) FROM OwnerMaster om JOIN Unit u ON om.unitId = u.id JOIN Society s ON om.societyId = s.id JOIN Area a ON om.areaId = a.id WHERE om.userId = ?1 AND om.userStatus = ?2 ORDER BY om.userStatus DESC")
    List<DashBoardVO> getUserRequestByStatus(int userId, String userStatus);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.DashBoardVO(om.userId, u.societyId, u.areaId, om.type, s.societyName, a.areaName, u.unit, s.address, s.landmark, s.societyBlock, s.city, s.pincode) FROM OwnerMaster om JOIN Unit u ON om.unitId = u.id JOIN Area a ON u.areaId = a.id JOIN Society s ON u.societyId = s.id AND om.unitId =?1 AND om.userId = ?2 AND om.userStatus = ?3")
    DashBoardVO getUserDashBoard(int unitId, int userId, String userStatus);

//    @Query(value = "SELECT new com.pz.mysociety.model.VO.UserListVO(om.userId, om.id, s.societyName, a.areaName, u.unit, om.type, om.occupancyStatus, om.userStatus, om.message, om.societyRole, om.timestamp) FROM OwnerMaster om JOIN Unit u ON om.unitId = u.id JOIN Society s ON om.societyId = s.id JOIN Area a ON u.areaId = a.id WHERE om.societyId =?1 AND om.userStatus =?2 ORDER BY om.id DESC")
//    List<UserListVO> getOwnerAndStatusList(int societyId, String userStatus, Pageable pageable);
//
//    @Query(value = "SELECT new com.pz.mysociety.model.VO.UserListVO(om.userId, om.id, s.societyName, a.areaName, u.unit, om.type, om.occupancyStatus, om.userStatus, om.message, om.societyRole, om.timestamp) FROM OwnerMaster om JOIN Unit u ON om.unitId = u.id JOIN Society s ON om.societyId = s.id JOIN Area a ON u.areaId = a.id WHERE om.societyId =?1 ORDER BY om.id DESC")
//    List<UserListVO> getOwnerList(int societyId, Pageable pageable);
//
//    @Query(value = "SELECT new com.paymentz.VO.VariableVO.UserListVO(om.id, sm.societyName, b.areaName, f.unit, u.name, u.mobileNumber, u.email, om.type, om.occupancyStatus, om.userStatus, om.message, om.timestamp) FROM OwnerMaster om JOIN Flat f ON om.unitId = f.id JOIN SocietyMaster sm ON om.societyId = sm.id JOIN Building b ON f.buildingId = b.id JOIN User u ON om.userId = u.id WHERE f.societyId =?1 AND f.unit =?2 ORDER BY om.id DESC")
//    List<UserListVO> getUserFlatList(int societyId, String unit); //this will fetch based on flatNumber
//
//    @Query(value = "SELECT new com.paymentz.VO.VariableVO.AllMemberVO(om.unitId, u.name, u.mobileNumber, b.areaName, f.unit, om.type) FROM OwnerMaster om JOIN Flat f ON om.unitId = f.id JOIN Building b ON f.buildingId = b.id JOIN User u ON om.userId = u.id WHERE om.societyId =?1 AND om.userStatus =?2 ")
//    List<AllMemberVO>  getMemberDetail(int societyId, String userStatus);
//
//    @Query(value = "SELECT new com.paymentz.VO.VariableVO.AllMemberVO(om.unitId, u.name, u.mobileNumber, b.areaName, f.unit, om.type) FROM OwnerMaster om JOIN Flat f ON om.unitId = f.id JOIN Building b ON f.buildingId = b.id JOIN User u ON om.userId = u.id WHERE om.societyId =?1 AND f.buildingId = ?2 AND om.userStatus =?3 ")
//    List<AllMemberVO>  getMemberDetailBuilding(int societyId, int buildingId, String userStatus);

    @Query(value = "SELECT om FROM OwnerMaster om WHERE om.unitId = ?1 AND om.userId = ?2 AND om.userStatus != ?3 AND om.isActive = ?4 ")
    OwnerMasterEntity findUserRequest(int unitId, int userId, String approved, boolean isActive);

    OwnerMasterEntity findByUserIdAndUnitId(int userId, int unitId);

    List<OwnerMasterEntity> findBySocietyIdAndUnitId(int societyId, int unitId);

    List<OwnerMasterEntity> findByUnitIdAndUserStatus(int unitId, String userApproved);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UserListVO(om.userId, om.type, u.unit, u.floor)  FROM OwnerMaster om JOIN Unit u ON om.unitId = u.id WHERE u.areaId = ?1 AND om.userStatus = ?2")
    List<UserListVO> getAllMember(int areaId, String userApproved, Pageable pageable);

    @Query(value = "SELECT COUNT(om) FROM OwnerMaster om JOIN Unit u ON om.unitId = u.id WHERE u.areaId = ?1 AND om.userStatus = ?2")
    int getOwnerCount(int areaId, String userStatus);

    int countBySocietyIdAndUserStatus(int societyId,String userStatus);


//    @Query(value = "SELECT new com.paymentz.VO.VariableVO.FrequentEntryVO(u.id, u.name, u.mobileNumber, u.photo) FROM OwnerMaster om JOIN User u ON om.userId = u.id WHERE om.unitId = ?1 AND om.userStatus = ?2")
//    List<FrequentEntryVO> getFlatIdAndUserStatus(int unitId, String userStatus);
//
//
//    @Query(value = "SELECT om FROM OwnerMaster om WHERE om.unitId = ?1 AND (om.userStatus = ?2 OR om.userStatus = ?3)")
//    List<OwnerMasterEntity> getListFlatIdAndUserStatus(int unitId, String approved, String pending);
//
//    @Modifying
//    @Query(value = "DELETE FROM OwnerMaster om WHERE om.unitId = ?1")
//    void getDeleteFlatId(int id);



//     @Modifying(clearAutomatically = true)
//     @Transactional
//     @Query(value = "UPDATE OwnerMaster om  SET om.societyRole = ?1 WHERE om.id = ?2")
//     void updateOwnerMemeber(String role, int ownerId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.UserListVO(om.userId, a.areaName, u.unit, u.floor, om.societyRole) FROM OwnerMaster om JOIN Unit u ON om.unitId = u.id JOIN Area a ON u.areaId = a.id WHERE om.societyId = ?1 AND om.societyRole != ?2 AND om.userStatus = ?3 AND om.isActive = ?4")
    List<UserListVO> getMemberRole(int societyId, String role, String userStatus, boolean isActive);

    @Query(value = "SELECT om.userId FROM OwnerMaster om WHERE om.societyId = ?1 AND om.userStatus = ?2 AND om.isActive = ?3")
    List<Integer> getUserId(int societyId, String userStatus, boolean isActive);

    int countBySocietyIdAndSocietyRoleAndUserStatusAndIsActive(int societyId, String societyRole, String userStatus, boolean isActive);


    @Query(value = "SELECT om.userId FROM  OwnerMaster  om WHERE om.unitId = ?1 AND om.userStatus = ?2 AND om.isActive = ?3")
    List<Integer> getMobileNumber(int unitId,String userStatus, boolean isActive);

    @Query(value = "SELECT om.userId FROM OwnerMaster om WHERE om.societyId = ?1 AND om.userStatus = ?2 AND om.isActive = ?3 AND om.unitId = ?4")
    List<Integer> getUserIdListByUnitId(int societyId, String userStatus, boolean isActive, int unitId);

    @Query(value = "SELECT om.userId FROM OwnerMaster om WHERE om.societyId = ?1 AND om.userStatus = ?2 AND om.isActive = ?3 AND om.unitId = ?4")
    int getUserIdByUnitId(int societyId, String userStatus, boolean isActive, int unitId);
}
