package by.modsen.priceservice.service;

import by.modsen.commonmodule.enumeration.CarCategory;
import by.modsen.priceservice.dto.response.PaginatedResponse;
import by.modsen.priceservice.dto.response.TariffResponse;
import by.modsen.priceservice.dto.response.TariffResponseList;
import by.modsen.priceservice.entity.Tariff;

import java.math.BigDecimal;

public interface TariffService {

    Tariff getByCarCategory(CarCategory carCategory);

    TariffResponse getById(Long id);

    TariffResponseList getAll();

    PaginatedResponse getAllPaginated(int pageNumber, int pageSize, String sortField);

    TariffResponse update(Long id, BigDecimal costPerKm);

}