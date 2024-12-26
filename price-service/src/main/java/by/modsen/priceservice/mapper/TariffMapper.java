package by.modsen.priceservice.mapper;

import by.modsen.priceservice.dto.request.TariffRequest;
import by.modsen.priceservice.dto.response.TariffResponse;
import by.modsen.priceservice.entity.Tariff;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TariffMapper {

    Tariff toEntity(TariffRequest tariffRequest);

    TariffResponse toDto(Tariff tariff);

    List<TariffResponse> toDtoList(List<Tariff> tariffs);

    void updateEntityFromDto(TariffRequest tariffRequest, @MappingTarget Tariff tariff);

}