package by.modsen.passengerservice.service.impl;

import by.modsen.passengerservice.client.PaymentClient;
import by.modsen.passengerservice.client.RatingClient;
import by.modsen.passengerservice.dto.request.CustomerRequest;
import by.modsen.passengerservice.dto.request.PassengerUpdateDto;
import by.modsen.passengerservice.dto.response.CustomerResponse;
import by.modsen.passengerservice.dto.response.PaginationDto;
import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.dto.response.PassengerDto;
import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.exception.PassengerNotFoundException;
import by.modsen.passengerservice.mapper.PassengerMapper;
import by.modsen.passengerservice.repository.PassengerRepository;
import by.modsen.passengerservice.service.PassengerService;
import by.modsen.passengerservice.utils.MessageUtils;
import by.modsen.passengerservice.utils.PassengerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository repository;
    private final PaymentClient paymentClient;
    private final RatingClient ratingClient;
    private final PassengerMapper passengerMapper;
    private final PassengerValidator validator;

    @Override
    public PassengerDto create(PassengerCreateDto passengerCreateDto) {
        Passenger passenger = passengerMapper.toPassenger(passengerCreateDto);
        CustomerRequest customerRequest = CustomerRequest.builder()
                .name(passenger.getFirstName())
                .balance(1000L)
                .build();

        CustomerResponse customerResponse = paymentClient.createCustomer(customerRequest);
        passenger.setCustomerId(customerResponse.customerId());
        passenger = repository.save(passenger);
        ratingClient.createPassengerRatingRecord(passenger.getId());

        return passengerMapper.toPassengerDto(passenger);
    }

    @Override
    public PassengerDto update(long id, PassengerUpdateDto passengerUpdateDto) {
        Passenger currentPassenger = getPassengerByIdOrThrow(id);
        validator.validateUniqueness(passengerUpdateDto, currentPassenger);
        passengerMapper.updatePassengerFromDto(passengerUpdateDto, currentPassenger);
        Passenger savedPassenger = repository.save(currentPassenger);

        return passengerMapper.toPassengerDto(savedPassenger);
    }

    @Override
    public PaginationDto<PassengerDto> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Passenger> page = repository.findAll(pageRequest);
        List<PassengerDto> data = page.getContent().stream()
                .map(passengerMapper::toPassengerDto)
                .toList();

        return new PaginationDto<>(
                data,
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
        );
    }

    @Override
    public PassengerDto getById(long id) {
        Passenger passenger = getPassengerByIdOrThrow(id);

        return passengerMapper.toPassengerDto(passenger);
    }

    @Override
    public void deleteById(long id) {
        getPassengerByIdOrThrow(id);
        repository.deleteById(id);
    }

    private Passenger getPassengerByIdOrThrow(long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(MessageUtils.PASSENGER_NOT_FOUND_ERROR, id));
    }
}