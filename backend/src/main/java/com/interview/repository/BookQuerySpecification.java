package com.interview.repository;

import com.interview.entity.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BookQuerySpecification implements Specification<Book> {
    private final String bookName;
    private final String authorName;
    private final String authorSurname;

    public BookQuerySpecification(String bookName, String authorName, String authorSurname) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
    }

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return Specification.where(
                ilikeSpecification("name", bookName)
        ).and(
                ilikeSpecification("authorName", authorName)
        ).and(
                ilikeSpecification("authorSurname", authorSurname)
        ).toPredicate(root, criteriaQuery, criteriaBuilder);
    }

    private Specification<Book> ilikeSpecification(String fieldName, String value) {
        return (root, query, builder) -> value == null || value.isEmpty() ? null :
                builder.like(builder.lower(root.get(fieldName)), containsLowerCase(value));
    }


    private String containsLowerCase(String bookName) {
        return "%" + bookName.toLowerCase() + "%";
    }
}
