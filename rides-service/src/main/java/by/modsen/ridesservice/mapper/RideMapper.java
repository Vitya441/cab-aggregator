package by.modsen.ridesservice.mapper;

import by.modsen.ridesservice.dto.request.RideRequest;
import by.modsen.ridesservice.dto.response.DriverWithCarDto;
import by.modsen.ridesservice.dto.response.RideResponse;
import by.modsen.ridesservice.dto.response.RideWithDriverResponse;
import by.modsen.ridesservice.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideMapper {

    @Mapping(target = "pickupAddress", source = "rideRequest.pickupAddress.address")
    @Mapping(target = "destinationAddress", source = "rideRequest.destinationAddress.address")
    Ride toEntity(RideRequest rideRequest);

    RideResponse toDto(Ride ride);

    @Mapping(target = "id", source = "ride.id") // Явно указываем, откуда брать id
    @Mapping(target = "status", source = "ride.status") // Явно указываем статус поездки
    @Mapping(target = "driver", source = "driverDto") // Водителя маппим напрямую
    RideWithDriverResponse toDtoWithDriver(Ride ride, DriverWithCarDto driverDto);

    List<RideResponse> toDtoList(List<Ride> rides);
}