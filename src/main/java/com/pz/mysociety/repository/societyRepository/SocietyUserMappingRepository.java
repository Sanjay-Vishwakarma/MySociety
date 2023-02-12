package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.SocietyUserEntity;
import com.pz.mysociety.model.VO.SocietyUserListVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Repository
public interface SocietyUserMappingRepository extends JpaRepository<SocietyUserEntity, Integer> {

    SocietyUserEntity findBySocietyUserId(int userId);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.SocietyUserListVO(su.id, su.societyId, su.societyUserId, su.adminStatus, s.societyName, s.address, s.landmark, s.societyBlock, s.state, s.city, s.pincode, su.timestamp) FROM SocietyUser su JOIN Society s ON su.societyId = s.id WHERE su.adminStatus = ?1 ORDER BY su.id DESC")
    List<SocietyUserListVO> getAdminStatus(String adminStatus, Pageable pageable);

    List<SocietyUserEntity> findBySocietyId(int societyId);

    List<SocietyUserEntity> findBySocietyIdAndAdminStatus(int societyId, String userDocumentPending);

    int countByAdminStatus(String adminStatus);

//    SocietyUserEntity findBySocietyUserIdAndSocietyRole(int societyId, String societyRole);

//    @Query(value = "SELECT su FROM SocietyUser su WHERE su.societyId = ?1 AND su.societyRole = ?2 AND su.adminStatus != ?3 AND su.isActive = ?4")
//    SocietyUserEntity getSocietyRole(int id, String societyRole, String adminStatus, boolean isActive);

    @Query(value = "SELECT su FROM SocietyUser su WHERE su.societyUserId = ?1 AND su.adminStatus != ?2")
    SocietyUserEntity getSocietyUserDetail(int societyUserId, String adminStatus);

    @Query(value = "SELECT su FROM SocietyUser su WHERE su.societyUserId = ?1 ORDER BY su.id DESC")
    List<SocietyUserEntity> getUser(int id, Pageable pageable);

    @Query(value = "SELECT su.societyUserId FROM SocietyUser su WHERE su.societyId = ?1 AND su.adminStatus = ?2")
    List<Integer> getSocietyUserId(int id, String userApproved, Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.SocietyUserListVO(su.id, su.societyId, su.societyUserId, su.adminStatus, s.societyName, s.address, s.landmark, s.societyBlock, s.state, s.city, s.pincode, su.timestamp) FROM SocietyUser su JOIN Society s ON su.societyId = s.id WHERE su.adminStatus = ?1 AND su.societyId IN ?2 AND su.societyUserId IN ?3 ORDER BY su.id DESC")
    List<SocietyUserListVO> getAdminBySocietyAndUser(String adminStatus, List<Integer> societyId, List<Integer> userId, Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.SocietyUserListVO(su.id, su.societyId, su.societyUserId, su.adminStatus, s.societyName, s.address, s.landmark, s.societyBlock, s.state, s.city, s.pincode, su.timestamp) FROM SocietyUser su JOIN Society s ON su.societyId = s.id WHERE su.adminStatus = ?1 AND su.societyId IN ?2 ORDER BY su.id DESC")
    List<SocietyUserListVO> getAdminBySocietyId(String adminStatus, List<Integer> societyId, Pageable pageable);

    @Query(value = "SELECT new com.pz.mysociety.model.VO.SocietyUserListVO(su.id, su.societyId, su.societyUserId, su.adminStatus, s.societyName, s.address, s.landmark, s.societyBlock, s.state, s.city, s.pincode, su.timestamp) FROM SocietyUser su JOIN Society s ON su.societyId = s.id WHERE su.adminStatus = ?1 AND su.societyUserId IN ?2 ORDER BY su.id DESC")
    List<SocietyUserListVO> getAdminByUserId(String adminStatus, List<Integer> userId, Pageable pageable);
}
