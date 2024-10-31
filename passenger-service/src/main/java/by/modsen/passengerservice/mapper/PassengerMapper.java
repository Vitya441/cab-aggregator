package by.modsen.passengerservice.mapper;

import by.modsen.passengerservice.dto.PassengerCreateDto;
import by.modsen.passengerservice.dto.PassengerDto;
import by.modsen.passengerservice.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerMapper {

    Passenger toPassenger(PassengerDto passengerDto);

    Passenger toPassenger(PassengerCreateDto passengerCreateDto);

    PassengerDto toPassengerDto(Passenger passenger);

    List<PassengerDto> toPassengerDtos(List<Passenger> passengers);

    void updatePassengerFromDto(PassengerCreateDto passengerCreateDto, @MappingTarget Passenger passenger);
}
