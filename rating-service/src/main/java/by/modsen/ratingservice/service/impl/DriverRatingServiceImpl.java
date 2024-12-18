package by.modsen.ratingservice.service.impl;

import by.modsen.ratingservice.dto.response.DriverRatingListResponse;
import by.modsen.ratingservice.dto.response.DriverRatingPage;
import by.modsen.ratingservice.dto.response.DriverRatingResponse;
import by.modsen.ratingservice.entity.DriverRating;
import by.modsen.ratingservice.exception.RatingAlreadyExistsException;
import by.modsen.ratingservice.exception.RatingNotFoundException;
import by.modsen.ratingservice.mapper.DriverRatingMapper;
import by.modsen.ratingservice.repository.DriverRatingRepository;
import by.modsen.ratingservice.service.DriverRatingService;
import by.modsen.ratingservice.util.ExceptionMessageConstants;
import by.modsen.ratingservice.util.RatingConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverRatingServiceImpl implements DriverRatingService {

    private final DriverRatingRepository repository;
    private final DriverRatingMapper mapper;

    @Override
    public DriverRatingResponse getRatingByDriverId(Long driverId) {
        DriverRating driverRating = getRecordByDriverIdOrThrow(driverId);

        return mapper.toDto(driverRating);
    }

    @Override
    public DriverRatingResponse createRatingRecord(Long driverId) {
        checkDriverRatingDoesntExist(driverId);
        DriverRating driverRating = new DriverRating();

        driverRating.setDriverId(driverId);
        driverRating.setRate(RatingConstants.DEFAULT_RATE);
        driverRating = repository.save(driverRating);

        return mapper.toDto(driverRating);
    }

    @Override
    public DriverRatingResponse updateRatingRecord(Long driverId, double rating) {
        DriverRating driverRating = getRecordByDriverIdOrThrow(driverId);
        double newRate = (driverRating.getRate() + rating) / 2;
        driverRating.setRate(newRate);
        driverRating = repository.save(driverRating);

        return mapper.toDto(driverRating);
    }

    @Override
    public void deleteRatingRecord(Long driverId) {
        DriverRating driverRating = getRecordByDriverIdOrThrow(driverId);

        repository.delete(driverRating);
    }

    @Override
    public DriverRatingListResponse getAllRatingRecords() {
        List<DriverRating> ratings = repository.findAll();
        List<DriverRatingResponse> ratingResponses = mapper.toDtoList(ratings);

        return new DriverRatingListResponse(ratingResponses);
    }

    @Override
    public DriverRatingPage getRatingsPage(int page, int size, String sortField) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortField));
        Page<DriverRating> ratingPage = repository.findAll(pageRequest);
        List<DriverRatingResponse> responseList = mapper.toDtoList(ratingPage.getContent());

        return DriverRatingPage.builder()
                .ratings(responseList)
                .currentPage(ratingPage.getNumber())
                .totalPages(ratingPage.getTotalPages())
                .totalElements(ratingPage.getTotalElements())
                .build();
    }

    private DriverRating getRecordByDriverIdOrThrow(Long driverId) {
        return repository
                .findByDriverId(driverId)
                .orElseThrow(() -> new RatingNotFoundException(ExceptionMessageConstants.DRIVER_RATING_NOT_FOUND, driverId));
    }

    private void checkDriverRatingDoesntExist(Long driverId) {
        if (repository.existsByDriverId(driverId)) {
            throw new RatingAlreadyExistsException(ExceptionMessageConstants.DRIVER_RATING_EXISTS, driverId);
        }
    }
}