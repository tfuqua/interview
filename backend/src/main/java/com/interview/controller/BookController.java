package com.interview.controller;

import com.interview.common.PageResponse;
import com.interview.common.exception.ResourceNotFoundException;
import com.interview.controller.payload.BookPayload;
import com.interview.controller.payload.UpsertBookPayload;
import com.interview.entity.Book;
import com.interview.repository.BookRepository;
import com.interview.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class BookController {
    private final BookService service;
    private final BookRepository repository;

    public BookController(BookService service, BookRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/books/{bookId}")
    public BookPayload get(@PathVariable Long bookId) {
        Book book = repository.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException(Book.class, bookId));
        return new BookPayload(book);
    }

    @GetMapping("/books")
    public PageResponse<BookPayload> list(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "authorname", required = false) String authorName,
            @RequestParam(name = "authorsurname", required = false) String authorSurname,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<Book> page = service.list(name, authorName, authorSurname, pageable);
        return new PageResponse(page.stream().map(BookPayload::new).collect(Collectors.toList()), page);
    }

    @PostMapping("/books")
    public ResponseEntity<Object> create(@Valid @RequestBody UpsertBookPayload payload) {
        Book book = service.create(payload);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(book.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Object> delete(@PathVariable Long bookId) {
        service.delete(bookId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books/{bookId}")
    public BookPayload update(@PathVariable Long bookId, @Valid @RequestBody UpsertBookPayload payload) {
        Book book = service.update(bookId, payload);
        return new BookPayload(book);
    }
}
