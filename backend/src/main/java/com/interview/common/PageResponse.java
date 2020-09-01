package com.interview.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@Getter
public class PageResponse<T extends ResponsePayload> {
    private List<T> pageResult;
    private Integer pageNumber;
    private Boolean nextPageExisting;

    public PageResponse(List<T> pageContent, Page page) {
        this.pageResult = pageContent;
        this.pageNumber = page.getNumber();
        this.nextPageExisting = page.hasNext();
    }
}
