package by.modsen.priceservice.dto.response;

import java.util.List;

public record TariffResponseList(
        List<TariffResponse> tariffs
) {}