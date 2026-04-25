package com.cg.repo;


import com.cg.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findByIsbn(String isbn);

    List<Inventory> findByPurchasedFalse();
}