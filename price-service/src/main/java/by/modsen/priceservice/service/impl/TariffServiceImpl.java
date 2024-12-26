package by.modsen.priceservice.service.impl;

import by.modsen.commonmodule.enumeration.CarCategory;
import by.modsen.priceservice.dto.response.PaginatedResponse;
import by.modsen.priceservice.dto.response.TariffResponse;
import by.modsen.priceservice.dto.response.TariffResponseList;
import by.modsen.priceservice.entity.Tariff;
import by.modsen.priceservice.mapper.TariffMapper;
import by.modsen.priceservice.repository.TariffRepository;
import by.modsen.priceservice.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;
    private final TariffMapper tariffMapper;

    @Override
    public Tariff getByCarCategory(CarCategory carCategory) {
        return tariffRepository
                .findByCarCategory(carCategory)
                .orElseThrow(() -> new RuntimeException("There is no tariff found for " + carCategory));
    }

    @Override
    public TariffResponse getById(Long id) {
        Tariff tariff = getTariffByIdOrThrow(id);
        return tariffMapper.toDto(tariff);
    }

    @Override
    public TariffResponseList getAll() {
        List<Tariff> tariffs = tariffRepository.findAll();
        List<TariffResponse> tariffResponses = tariffMapper.toDtoList(tariffs);
        return new TariffResponseList(tariffResponses);
    }

    @Override
    public PaginatedResponse getAllPaginated(int pageNumber, int pageSize, String sortField) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortField));
        Page<Tariff> page = tariffRepository.findAll(pageRequest);
        List<TariffResponse> responseList = tariffMapper.toDtoList(page.getContent());

        return PaginatedResponse.builder()
                .tariffs(responseList)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }


    @Override
    public TariffResponse update(Long id, BigDecimal costPerKm) {
        Tariff tariff = getTariffByIdOrThrow(id);
        tariff.setCostPerKm(costPerKm);
        Tariff savedTariff =  tariffRepository.save(tariff);
        return tariffMapper.toDto(savedTariff);
    }

    private Tariff getTariffByIdOrThrow(Long id) {
        return tariffRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Tariff not found with id = " + id));
    }
}