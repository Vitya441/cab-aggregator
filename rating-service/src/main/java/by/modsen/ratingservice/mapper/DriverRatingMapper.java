package by.modsen.ratingservice.mapper;

import by.modsen.ratingservice.dto.response.DriverRatingResponse;
import by.modsen.ratingservice.entity.DriverRating;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverRatingMapper {

    DriverRatingResponse toDto(DriverRating driverRating);

    List<DriverRatingResponse> toDtoList(List<DriverRating> ratings);

}