package com.interview.controller.payload;

import com.interview.common.ResponsePayload;
import com.interview.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class BookPayload implements ResponsePayload {
    private Long id;
    private String name;
    private String authorName;
    private String authorSurname;
    private String imageUrl;
    private LocalDate publishDate;

    public BookPayload(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.authorName = book.getAuthorName();
        this.authorSurname = book.getAuthorSurname();
        this.imageUrl = book.getImageUrl();
        this.publishDate = book.getPublishDate();
    }
}
