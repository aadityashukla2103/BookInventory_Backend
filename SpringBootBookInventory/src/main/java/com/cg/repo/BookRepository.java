package com.cg.repo;

import com.cg.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByCategory_CatId(Integer catId);

    List<Book> findByPublisher_PublisherId(Integer publisherId);
}
