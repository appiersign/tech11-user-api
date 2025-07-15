package com.appiersign.dtos;

import java.util.List;

public class PaginatedResource<T> {
    private List<T> data;
    private Long total;
    private Integer pageSize;
    private Integer pageNumber;

    public PaginatedResource(List<T> data, Long total, Integer pageSize, Integer pageNumber) {
        this.data = data;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    public List<T> getData() {
        return data;
    }

    public Long getTotal() {
        return total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }
}
