package by.modsen.ridesservice.mapper;

import by.modsen.ridesservice.dto.request.RideRequest;
import by.modsen.ridesservice.dto.response.RideResponse;
import by.modsen.ridesservice.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideMapper {

    Ride toEntity(RideRequest rideRequest);

    RideResponse toDto(Ride ride);

    List<RideResponse> toDtoList(List<Ride> rides);
}
