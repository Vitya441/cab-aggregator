package by.modsen.driverservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum CarSort {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    BRAND_ASC(Sort.by(Sort.Direction.ASC, "brand")),
    BRAND_DESC(Sort.by(Sort.Direction.DESC, "brand")),
    LICENSE_ASC(Sort.by(Sort.Direction.ASC, "licenseNumber")),
    LICENSE_DESC(Sort.by(Sort.Direction.DESC, "licenseNumber"));

    private final Sort sortValue;
}
