package com.cg.repo;


import com.cg.entity.PermRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermRoleRepository extends JpaRepository<PermRole, Integer> {
}