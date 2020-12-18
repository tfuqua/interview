package com.interview.repository;

import com.interview.model.Movie;
import net.kaczmarzyk.spring.data.jpa.domain.EqualIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "title", params = "title", spec = Like.class),
        @Spec(path = "year", params = "year", spec = EqualIgnoreCase.class)
})
public interface MovieSpecification extends Specification<Movie> {
}
