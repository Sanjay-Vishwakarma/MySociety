package com.pz.mysociety.repository.societyRepository;

import com.pz.mysociety.entity.societyEntity.SocietyReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SocietyReviewRepository extends JpaRepository<SocietyReviewEntity,Integer> {
    SocietyReviewEntity findByOwnerIdAndId(int ownerId,int id);
    SocietyReviewEntity findByOwnerId(int ownerId);
   // SocietyReviewEntity findByOwnerId(int ownerId, int unitId);

    SocietyReviewEntity findById( int id);

}
