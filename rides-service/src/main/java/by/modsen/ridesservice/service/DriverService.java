package by.modsen.ridesservice.service;

import by.modsen.ridesservice.dto.response.DriverDto;
import by.modsen.ridesservice.dto.response.DriverWithCarDto;

public interface DriverService {

    DriverDto getDriverById(Long id);

    DriverWithCarDto getByIdWithCar(Long id);
}