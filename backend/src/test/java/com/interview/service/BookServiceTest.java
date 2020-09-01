package com.interview.service;

import com.interview.common.exception.ResourceNotFoundException;
import com.interview.controller.payload.UpsertBookPayload;
import com.interview.entity.Book;
import com.interview.repository.BookRepository;
import com.interview.service.exception.DuplicateBookNameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @Test
    public void testCreate() {
        given(repository.existsByNameIgnoreCase("test")).willReturn(false);
        service.create(new UpsertBookPayload("test", "author", "surname",
                "url", LocalDate.of(2010, 1, 3)));
        ArgumentCaptor<Book> savedBook = ArgumentCaptor.forClass(Book.class);
        verify(repository).save(savedBook.capture());
        assertEquals("test", savedBook.getValue().getName());
        assertEquals("author", savedBook.getValue().getAuthorName());
        assertEquals("surname", savedBook.getValue().getAuthorSurname());
        assertEquals("url", savedBook.getValue().getImageUrl());
        assertEquals(LocalDate.of(2010, 1, 3), savedBook.getValue().getPublishDate());
    }

    @Test
    public void testCreateNameAlreadyExisting() {
        given(repository.existsByNameIgnoreCase("test")).willReturn(true);
        assertThrows(DuplicateBookNameException.class, () -> {
            service.create(new UpsertBookPayload("test", "author", "surname",
                    "url", LocalDate.of(2010, 1, 3)));
        });
    }

    @Test
    public void testDelete() {
        given(repository.findById(1L)).willReturn(Optional.of(new Book("tests", "author", "surname",
                "url", LocalDate.of(2010, 1, 3))));
        service.delete(1L);
        ArgumentCaptor<Book> deletedBook = ArgumentCaptor.forClass(Book.class);
        verify(repository).delete(deletedBook.capture());
        assertEquals("tests", deletedBook.getValue().getName());
    }

    @Test
    public void testDeleteBookNotFound() {
        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(1L);
        });
    }

    @Test
    public void testUpdate() {
        Book existingBook = new Book("test_updated", "author", "surname",
                "url", LocalDate.of(2010, 1, 3));
        existingBook.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(existingBook));
        given(repository.findByNameIgnoreCase("test_updated")).willReturn(Optional.of(existingBook));
        service.update(1L, new UpsertBookPayload("test_updated", "author_updated", "surname_updated",
                "url_updated", LocalDate.of(2010, 1, 4)));
        ArgumentCaptor<Book> updatedBook = ArgumentCaptor.forClass(Book.class);
        verify(repository).save(updatedBook.capture());
        assertEquals("test_updated", updatedBook.getValue().getName());
        assertEquals("author_updated", updatedBook.getValue().getAuthorName());
        assertEquals("surname_updated", updatedBook.getValue().getAuthorSurname());
        assertEquals("url_updated", updatedBook.getValue().getImageUrl());
        assertEquals(LocalDate.of(2010, 1, 4), updatedBook.getValue().getPublishDate());
    }

    @Test
    public void testUpdateBookNotFound() {
        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
            service.update(1L, new UpsertBookPayload("test_updated", "author_updated", "surname_updated",
                    "url_updated", LocalDate.of(2010, 1, 4)));
        });
    }

    @Test
    public void testUpdateBookSameNameAlreadyExisting() {
        given(repository.findById(1L)).willReturn(Optional.of(new Book("tests", "author", "surname",
                "url", LocalDate.of(2010, 1, 3))));
        Book existingBook = new Book("test_updated", "author2", "surname2",
                "url", LocalDate.of(2016, 2, 4));
        existingBook.setId(2L);
        given(repository.findByNameIgnoreCase("test_updated")).willReturn(Optional.of(existingBook));
        assertThrows(DuplicateBookNameException.class, () -> {
            service.update(1L, new UpsertBookPayload("test_updated", "author_updated", "surname_updated",
                    "url_updated", LocalDate.of(2010, 1, 4)));
        });
    }
}
