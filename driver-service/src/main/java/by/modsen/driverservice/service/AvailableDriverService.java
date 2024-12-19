package by.modsen.driverservice.service;

import by.modsen.driverservice.dto.response.DriverDto;

public interface AvailableDriverService {

    DriverDto getAvailableDriver(String pickupLocation);

}