package com.interview.core.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PaginatedResponsePayload<T, S> implements ResponsePayload {
    private List<S> data;
    private PageMetaData metaData;

    public PaginatedResponsePayload(Page<T> page, Function<T, S> mapper) {
        this.data = page.getContent().stream().map(mapper).collect(Collectors.toList());
        this.metaData = new PageMetaData(!page.isLast(), page.getTotalElements());
    }
}
