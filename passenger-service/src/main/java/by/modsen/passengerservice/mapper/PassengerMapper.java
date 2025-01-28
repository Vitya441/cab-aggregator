package by.modsen.passengerservice.mapper;

import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.dto.request.PassengerUpdateDto;
import by.modsen.passengerservice.dto.response.PassengerDto;
import by.modsen.passengerservice.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerMapper {

    Passenger toPassenger(PassengerCreateDto passengerCreateDto);

    PassengerDto toPassengerDto(Passenger passenger);

    List<PassengerDto> toPassengerDtos(List<Passenger> passengers);

    void updatePassengerFromDto(PassengerUpdateDto passengerUpdateDto, @MappingTarget Passenger passenger);
}
