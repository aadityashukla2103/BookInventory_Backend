package com.cg.repo;

import com.cg.entity.BookReview;
import com.cg.entity.BookReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, BookReviewId> {

	List<BookReview> findByIdIsbn(String isbn);

	List<BookReview> findByIdReviewerId(Integer reviewerId);
}