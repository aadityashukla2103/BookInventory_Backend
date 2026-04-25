package com.cg.repo;

import com.cg.entity.BookAuthor;
import com.cg.entity.BookAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {

	List<BookAuthor> findByIdIsbn(String isbn);

	List<BookAuthor> findByIdAuthorId(Integer authorId);
}