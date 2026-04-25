package com.cg.repo;

import com.cg.entity.PurchaseLog;
import com.cg.entity.PurchaseLogId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseLogRepository extends JpaRepository<PurchaseLog, PurchaseLogId> {

	List<PurchaseLog> findByIdUserId(Integer userId);

	List<PurchaseLog> findByIdInventoryId(Integer inventoryId);
}
