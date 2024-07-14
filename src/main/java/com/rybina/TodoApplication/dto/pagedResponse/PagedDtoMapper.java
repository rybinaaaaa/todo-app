package com.rybina.TodoApplication.dto.pagedResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PagedDtoMapper<F, T> {

    public PagedRead<T> mapToPagedRead(Page<F> page, List<T> content) {
        return PagedRead.<T>builder()
                .content(content)
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .pageNumber(page.getNumber())
                .build();
    }

    public PagedRead<F> mapToPagedRead(Page<F> page) {
        return PagedRead.<F>builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .pageSize(page.getSize())
                .pageNumber(page.getNumber())
                .build();
    }
}
