package com.example.task_manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;


@Schema(description = "Generic paginated response wrapper")
public record PagedResponse<T>(
        @Schema(description = "The items on this page")
        List<T> content,

        @Schema(description = "Total number of items across all pages", example = "47")
        long totalElements,

        @Schema(description = "Total number of pages", example = "3")
        int totalPages,

        @Schema(description = "Current page number (zero-indexed)", example = "0")
        int currentPage,

        @Schema(description = "Number of items per page", example = "20")
        int pageSize
) {
    public static <E, T> PagedResponse<T> from(Page<E> page, Function<E, T> mapper) {
        return new PagedResponse<>(
                page.getContent().stream().map(mapper).toList(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }
}
