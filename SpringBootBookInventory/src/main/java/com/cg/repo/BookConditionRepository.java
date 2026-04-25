package com.cg.repo;

import com.cg.entity.BookCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookConditionRepository extends JpaRepository<BookCondition, Integer> {
}