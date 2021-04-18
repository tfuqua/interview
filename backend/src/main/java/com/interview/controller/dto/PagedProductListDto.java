package com.interview.controller.dto;

import java.util.List;

public class PagedProductListDto {
    private List<ProductDto> data;
    private PageDto pagination;

    public PagedProductListDto(List<ProductDto> dtoList, long totalCount, int limit, int pageNumber) {
        this.data = dtoList;
        this.pagination = new PageDto(totalCount,limit,pageNumber);
    }

    public List<ProductDto> getData() {
        return data;
    }

    public PageDto getPagination() {
        return pagination;
    }

    private class PageDto {
        private long totalCount;
        private int limit;
        private int pageNumber;

        public PageDto(long totalCount, int limit, int pageNumber) {
            this.totalCount = totalCount;
            this.limit = limit;
            this.pageNumber = pageNumber;
        }

        public long getTotalCount() {
            return totalCount;
        }

        public int getLimit() {
            return limit;
        }

        public int getPageNumber() {
            return pageNumber;
        }
    }
}
