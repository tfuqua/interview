package com.interview.repository;


import com.interview.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookRepositoryIntegrationTest {
    @Autowired
    private BookRepository repository;

    @Test
    @Transactional
    public void testBookQuerySpecification() {
        repository.save(new Book("book1", "ali", "saglam", "url", LocalDate.now()));
        repository.save(new Book("book2", "veli", "saglam", "url", LocalDate.now()));

        List<Book> result = repository.findAll(new BookQuerySpecification("book", null, null));
        assertEquals(2, result.size());
        result = repository.findAll(new BookQuerySpecification("book1", null, null));
        assertEquals(1, result.size());
        result = repository.findAll(new BookQuerySpecification("book2", null, null));
        assertEquals(1, result.size());
        result = repository.findAll(new BookQuerySpecification("book", null, "saglam"));
        assertEquals(2, result.size());
        result = repository.findAll(new BookQuerySpecification("book", "a", "saglam"));
        assertEquals(1, result.size());
        result = repository.findAll(new BookQuerySpecification("book", "li", "saglam"));
        assertEquals(2, result.size());
        result = repository.findAll(new BookQuerySpecification("book", "tli", "saglam"));
        assertEquals(0, result.size());
    }
}
