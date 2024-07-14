package com.rybina.TodoApplication.dto.pagedResponse;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PagedRead<T> {

    Integer totalPages;
    Integer pageNumber;
    Integer pageSize;

    List<T> content;
}
