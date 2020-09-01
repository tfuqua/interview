package com.interview.service;


import com.interview.controller.payload.UpsertBookPayload;
import com.interview.entity.Book;
import com.interview.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceIntegrationTest {
    @Autowired
    private BookService service;

    @Autowired
    private BookRepository repository;

    @Test
    @Transactional
    public void testCRUD() {
        UpsertBookPayload payload1 = new UpsertBookPayload("test", "author", "surname",
                 "url", LocalDate.of(2010, 1, 3));
        UpsertBookPayload payload2= new UpsertBookPayload("test2", "author", "surname",
                 "url", LocalDate.of(2010, 1, 3));
        service.create(payload1);
        service.create(payload2);
        List<Book> books = repository.findAll();
        assertEquals(2, books.size());
        assertTrue(books.stream().allMatch((book -> book.getId() != null)));
        assertTrue(books.stream().allMatch((book -> book.getCreatedAt() != null)));
        assertTrue(books.stream().allMatch((book -> book.getUpdatedAt() != null)));
        // test update
        Book book1 = books.get(0);
        Instant previousUpdatedAt = book1.getUpdatedAt();
        Instant previousCreatedAt = book1.getCreatedAt();
        Book updatedBook = service.update(book1.getId(), new UpsertBookPayload("updated_name", "author", "surname",
                "url", LocalDate.of(2010, 1, 3)));
        repository.flush();
        assertEquals(previousCreatedAt, updatedBook.getCreatedAt());
        assertNotEquals(previousUpdatedAt, updatedBook.getUpdatedAt());
        assertEquals("updated_name", updatedBook.getName());

        // test delete
        service.delete(book1.getId());
        assertEquals(1, repository.findAll().size());
        assertEquals("test2", repository.findAll().get(0).getName());
    }
}
