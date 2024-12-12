package by.modsen.ridesservice.dto;

import java.util.List;

public record PaginationDto<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {}
