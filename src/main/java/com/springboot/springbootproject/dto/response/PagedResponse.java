package com.springboot.springbootproject.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static <T> PagedResponse<T> fromPage(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
