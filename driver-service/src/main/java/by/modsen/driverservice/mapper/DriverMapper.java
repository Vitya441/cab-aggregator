package by.modsen.driverservice.mapper;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.DriverWithCarDto;
import by.modsen.driverservice.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CarMapper.class})
public interface DriverMapper {

    Driver toEntity(DriverCreateDto driverCreateDto);

    DriverDto toDto(Driver driver);

    List<DriverDto> toDtoList(List<Driver> drivers);

    DriverWithCarDto toDriverWithCarDto(Driver driver);

    List<DriverWithCarDto> toDriverWithCarDtoList(List<Driver> drivers);

    void updateEntityFromDto(DriverCreateDto driverCreateDto, @MappingTarget Driver driver);

}