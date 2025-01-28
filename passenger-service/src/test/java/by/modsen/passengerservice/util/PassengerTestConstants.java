package by.modsen.passengerservice.util;

import by.modsen.passengerservice.dto.request.CustomerRequest;
import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.dto.request.PassengerUpdateDto;
import by.modsen.passengerservice.dto.response.CustomerResponse;
import by.modsen.passengerservice.dto.response.PaginationDto;
import by.modsen.passengerservice.dto.response.PassengerDto;
import by.modsen.passengerservice.entity.Passenger;

import java.util.List;

public class PassengerTestConstants {

    public static final Long PASSENGER_ID = 1L;
    public static final String FIRST_NAME = "Viktor";
    public static final String LAST_NAME = "Maksimov";
    public static final String PHONE = "+375292653480";
    public static final String CUSTOMER_ID = "1zlisq21gokdfs";
    public static final Long BALANCE = 1000L;
    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 10;
    public static final int CURRENT_PAGE = 0;
    public static final int TOTAL_PAGES = 1;
    public static final int TOTAL_ITEMS = 2;
    public static final int CURRENT_PAGE_SIZE = 2;

    public static final String PASSENGER_PATH = "/api/v1/passengers";
    public static final String RATING_PATH = "/api/v1/ratings/passengers";
    public static final String PAYMENT_PATH = "/api/v1/payments/customers";

    public static PassengerCreateDto getPassengerCreateDto() {
        return PassengerCreateDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    public static PassengerUpdateDto getPassengerUpdateDto() {
        return new PassengerUpdateDto(PHONE);
    }

    public static Passenger getPassenger() {
        return Passenger.builder()
                .id(PASSENGER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    public static Passenger getPassengerWithPhone() {
        return Passenger.builder()
                .id(PASSENGER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .build();
    }

    public static Passenger getUpdatedPassenger() {
        return Passenger.builder()
                .id(PASSENGER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .build();
    }

    public static PassengerDto getPassengerDto() {
        return PassengerDto.builder()
                .id(PASSENGER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    public static PassengerDto getUpdatedPassengerDto() {
        return PassengerDto.builder()
                .id(PASSENGER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .build();
    }

    public static CustomerRequest getCustomerRequest() {
        return CustomerRequest.builder()
                .name(FIRST_NAME)
                .balance(BALANCE)
                .build();
    }

    public static CustomerResponse getCustomerResponse() {
        return CustomerResponse.builder()
                .customerId(CUSTOMER_ID)
                .name(FIRST_NAME)
                .balance(BALANCE)
                .build();
    }

    public static List<Passenger> getPassengerList() {
        return List.of(
                Passenger.builder().id(1L).firstName("Viktor").lastName("Maksimov").build(),
                Passenger.builder().id(2L).firstName("John").lastName("Doe").build()
        );
    }

    public static List<PassengerDto> getPassengerDtoList() {
        return List.of(
                PassengerDto.builder().id(1L).firstName("Viktor").lastName("Maksimov").build(),
                PassengerDto.builder().id(2L).firstName("John").lastName("Doe").build()
        );
    }

    public static PaginationDto<PassengerDto> getPaginatedResponse() {
        return new PaginationDto<>(
                getPassengerDtoList(),
                CURRENT_PAGE,
                TOTAL_PAGES,
                TOTAL_ITEMS,
                CURRENT_PAGE_SIZE
        );
    }
}