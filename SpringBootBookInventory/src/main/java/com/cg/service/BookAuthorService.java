package com.cg.service;

import com.cg.dto.BookAuthorDto;
import com.cg.entity.Author;
import com.cg.entity.Book;
import com.cg.entity.BookAuthor;
import com.cg.entity.BookAuthorId;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.AuthorRepository;
import com.cg.repo.BookAuthorRepository;
import com.cg.repo.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorService {
	private final BookAuthorRepository bookAuthorRepository;
	private final BookRepository bookRepository;
	private final AuthorRepository authorRepository;

	public BookAuthorService(BookAuthorRepository bookAuthorRepository, BookRepository bookRepository,
			AuthorRepository authorRepository) {
		this.bookAuthorRepository = bookAuthorRepository;
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
	}

	public List<BookAuthorDto> getAll() {
		return bookAuthorRepository.findAll().stream().map(this::toDto).toList();
	}

	public BookAuthorDto getById(String isbn, Integer authorId) {
		return toDto(findEntity(new BookAuthorId(isbn, authorId)));
	}

	public BookAuthorDto create(BookAuthorDto dto) {
		BookAuthor entity = new BookAuthor();
		apply(dto, entity);
		return toDto(bookAuthorRepository.save(entity));
	}

	public BookAuthorDto update(String isbn, Integer authorId, BookAuthorDto dto) {
		BookAuthor entity = findEntity(new BookAuthorId(isbn, authorId));
		apply(dto, entity);
		return toDto(bookAuthorRepository.save(entity));
	}

	public void delete(String isbn, Integer authorId) {
		bookAuthorRepository.delete(findEntity(new BookAuthorId(isbn, authorId)));
	}

	private BookAuthor findEntity(BookAuthorId id) {
		return bookAuthorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
				"BookAuthor not found for isbn " + id.getISBN() + " and author " + id.getAuthorID()));
	}

	private void apply(BookAuthorDto dto, BookAuthor entity) {
		Book book = bookRepository.findById(dto.getIsbn())
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + dto.getIsbn()));
		Author author = authorRepository.findById(dto.getAuthorID())
				.orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + dto.getAuthorID()));
		entity.setId(new BookAuthorId(dto.getIsbn(), dto.getAuthorID()));
		entity.setBook(book);
		entity.setAuthor(author);
		entity.setPrimaryAuthor(dto.getPrimaryAuthor());
	}

	private BookAuthorDto toDto(BookAuthor entity) {
		BookAuthorDto dto = new BookAuthorDto();
		dto.setIsbn(entity.getId() != null ? entity.getId().getISBN() : null);
		dto.setAuthorID(entity.getId() != null ? entity.getId().getAuthorID() : null);
		dto.setPrimaryAuthor(entity.getPrimaryAuthor());
		return dto;
	}
}