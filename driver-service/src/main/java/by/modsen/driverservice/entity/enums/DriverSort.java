package by.modsen.driverservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum DriverSort {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    FIRSTNAME_ASC(Sort.by(Sort.Direction.ASC, "firstName")),
    FIRSTNAME_DESC(Sort.by(Sort.Direction.DESC, "firstName")),
    LASTNAME_ASC(Sort.by(Sort.Direction.ASC, "lastName")),
    LASTNAME_DESC(Sort.by(Sort.Direction.DESC, "lastName"));

    private final Sort sortValue;
}
