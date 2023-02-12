package com.pz.mysociety.repository.maintenanceRepository;

import com.pz.mysociety.entity.maintenanceEntity.SocietyInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface SocietyInvoiceRepository extends JpaRepository<SocietyInvoiceEntity, Integer>, JpaSpecificationExecutor<SocietyInvoiceEntity> {
    SocietyInvoiceEntity findByUnitIdAndMonthAndYear(int unitId, String month, String year);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE society_invoice sm SET sm.invoice_status = ?2 WHERE sm.id IN ?1 AND sm.invoice_status = ?3 ")
    int updateStatus(ArrayList<Integer> invoiceId, String sent, String uploaded);

}
