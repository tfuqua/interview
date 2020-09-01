package com.interview.repository;

import com.interview.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Boolean existsByNameIgnoreCase(String name);
    Optional<Book> findByNameIgnoreCase(String bookName);
}
