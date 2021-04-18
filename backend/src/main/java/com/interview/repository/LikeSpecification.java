package com.interview.repository;

import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class LikeSpecification<T> implements Specification<T> {

    public static final String LIKE_OPERATOR = "%";

    private String key;
    private String value;

    private LikeSpecification(String key, String value) {
        this.value = value;
        this.key = key;
    }

    public static <T> LikeSpecification<T> of(String key, String value) {
        return new LikeSpecification(key,value);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        return builder.like(builder.lower(root.get(key)), LIKE_OPERATOR + value.toLowerCase() + LIKE_OPERATOR);
    }
}

