package by.modsen.ratingservice.service.impl;

import by.modsen.ratingservice.dto.response.PassengerRatingListResponse;
import by.modsen.ratingservice.dto.response.PassengerRatingPage;
import by.modsen.ratingservice.dto.response.PassengerRatingResponse;
import by.modsen.ratingservice.entity.PassengerRating;
import by.modsen.ratingservice.exception.RatingAlreadyExistsException;
import by.modsen.ratingservice.exception.RatingNotFoundException;
import by.modsen.ratingservice.mapper.PassengerRatingMapper;
import by.modsen.ratingservice.repository.PassengerRatingRepository;
import by.modsen.ratingservice.service.PassengerRatingService;
import by.modsen.ratingservice.util.ExceptionMessageConstants;
import by.modsen.ratingservice.util.RatingConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerRatingServiceImpl implements PassengerRatingService {

    private final PassengerRatingRepository repository;
    private final PassengerRatingMapper mapper;

    @Override
    public PassengerRatingResponse createRatingRecord(Long passengerId) {
        checkPassengerRatingDoesntExist(passengerId);
        PassengerRating passengerRating = new PassengerRating();
        passengerRating.setPassengerId(passengerId);
        passengerRating.setRate(RatingConstants.DEFAULT_RATE);
        passengerRating = repository.save(passengerRating);

        return mapper.toDto(passengerRating);
    }

    @Override
    public PassengerRatingResponse updateRatingRecord(Long passengerId, double rating) {
        PassengerRating passengerRating = getRatingByPassengerIdOrThrow(passengerId);
        double newRate = (passengerRating.getRate() + rating) / 2;
        passengerRating.setRate(newRate);
        passengerRating = repository.save(passengerRating);

        return mapper.toDto(passengerRating);
    }

    @Override
    public PassengerRatingResponse getRatingByPassengerId(Long passengerId) {
        PassengerRating passengerRating = getRatingByPassengerIdOrThrow(passengerId);

        return mapper.toDto(passengerRating);
    }

    @Override
    public PassengerRatingListResponse getAllRatingRecords() {
        List<PassengerRatingResponse> ratings = mapper.toDtoList(repository.findAll());

        return new PassengerRatingListResponse(ratings);
    }

    @Override
    public PassengerRatingPage getRatingsPage(int page, int size, String sortField) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortField));
        Page<PassengerRatingResponse> ratingPage = repository
                .findAll(pageRequest)
                .map(mapper::toDto);

        return PassengerRatingPage.builder()
                .ratings(ratingPage.getContent())
                .currentPage(ratingPage.getNumber())
                .totalPages(ratingPage.getTotalPages())
                .totalElements(ratingPage.getTotalElements())
                .build();
    }

    @Override
    public void deleteRatingRecord(Long passengerId) {
        PassengerRating passengerRating = getRatingByPassengerIdOrThrow(passengerId);
        repository.delete(passengerRating);
    }

    private PassengerRating getRatingByPassengerIdOrThrow(Long passengerId) {
        return repository
                .findByPassengerId(passengerId)
                .orElseThrow(() -> new RatingNotFoundException(ExceptionMessageConstants.PASSENGER_RATING_NOT_FOUND, passengerId));
    }

    private void checkPassengerRatingDoesntExist(Long passengerId) {
        if (repository.existsByPassengerId(passengerId)) {
            throw new RatingAlreadyExistsException(ExceptionMessageConstants.PASSENGER_RATING_EXISTS, passengerId);
        }
    }
}