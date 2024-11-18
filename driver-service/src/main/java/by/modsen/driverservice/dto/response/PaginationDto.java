package by.modsen.driverservice.dto.response;

import java.util.List;

public record PaginationDto<T>(
        List<T> data,
        int currentPage,
        int totalPages,
        long totalItems,
        int pageSize
) {}
