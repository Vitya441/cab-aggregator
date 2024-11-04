package by.modsen.passengerservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginationDto<T> {

    private List<T> data;

    private int currentPage;

    private int totalPages;

    private long totalItems;

    private int pageSize;
}