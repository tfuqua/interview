package com.interview.service;

import com.interview.common.exception.ResourceNotFoundException;
import com.interview.controller.payload.UpsertBookPayload;
import com.interview.entity.Book;
import com.interview.repository.BookQuerySpecification;
import com.interview.repository.BookRepository;
import com.interview.service.exception.DuplicateBookNameException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Book create(UpsertBookPayload payload) {
        Boolean isNameExisting = repository.existsByNameIgnoreCase(payload.getName());
        if (isNameExisting) {
            throw new DuplicateBookNameException(payload.getName());
        }
        return repository.save(new Book(payload.getName(), payload.getAuthorName(),
                payload.getAuthorSurname(), payload.getImageUrl(), payload.getPublishDate()));
    }

    @Transactional(readOnly = true)
    public Page<Book> list(String bookName, String authorName, String authorSurname, Pageable pageable) {
        return repository.findAll(
                new BookQuerySpecification(bookName, authorName, authorSurname), pageable
        );
    }

    @Transactional
    public void delete(Long bookId) {
        Book book = getBook(bookId);
        repository.delete(book);
    }

    @Transactional
    public Book update(Long bookId, UpsertBookPayload payload) {
        Book book = getBook(bookId);
        checkBookNameExisting(payload.getName(), book);
        book.setName(payload.getName());
        book.setAuthorName(payload.getAuthorName());
        book.setAuthorSurname(payload.getAuthorSurname());
        book.setImageUrl(payload.getImageUrl());
        book.setPublishDate(payload.getPublishDate());
        return repository.save(book);
    }

    private void checkBookNameExisting(String bookName, Book book) {
        repository.findByNameIgnoreCase(bookName).ifPresent(it -> {
            if (!it.getId().equals(book.getId())) {
                throw new DuplicateBookNameException(bookName);
            }
        });
    }

    private Book getBook(Long bookId) {
        return repository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException(Book.class, bookId));
    }
}
