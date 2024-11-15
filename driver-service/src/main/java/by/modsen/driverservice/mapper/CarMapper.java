package by.modsen.driverservice.mapper;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    Car toEntity(CarCreateDto carCreateDto);

    CarDto toDto(Car car);

    List<CarDto> toDtoList(List<Car> cars);

    void updateEntityFromDto(CarCreateDto carCreateDto, @MappingTarget Car car);

}