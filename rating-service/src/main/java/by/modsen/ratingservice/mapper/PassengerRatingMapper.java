package by.modsen.ratingservice.mapper;

import by.modsen.ratingservice.dto.response.PassengerRatingResponse;
import by.modsen.ratingservice.entity.PassengerRating;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerRatingMapper {

    PassengerRatingResponse toDto(PassengerRating passengerRating);

    List<PassengerRatingResponse> toDtoList(List<PassengerRating> ratings);

}