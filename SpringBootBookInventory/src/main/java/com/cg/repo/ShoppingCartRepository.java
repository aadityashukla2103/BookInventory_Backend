package com.cg.repo;

import com.cg.entity.ShoppingCart;
import com.cg.entity.ShoppingCartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, ShoppingCartId> {

	List<ShoppingCart> findByIdUserId(Integer userId);

	List<ShoppingCart> findByIdIsbn(String isbn);
}