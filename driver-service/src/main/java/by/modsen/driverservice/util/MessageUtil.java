package by.modsen.driverservice.util;

import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
    public static final String DRIVER_NOT_FOUND = "driver.notFound";
    public static final String CAR_NOT_FOUND = "car.notFound";
    public static final String PHONE_EXISTS = "driver.phoneExists";
    public static final String LICENSE_NUMBER_EXISTS = "car.licenseNumberExists";
    public static final String CAR_ALREADY_ASSIGNED = "car.alreadyAssigned";

    public static final String VALIDATION_LICENCE_NUMBER="car.licenseNumber";
    public static final String VALIDATION_LICENCE_NUMBER_SIZE="car.licenseNumber.size";
    public static final String VALIDATION_COLOR="car.color";
    public static final String VALIDATION_COLOR_SIZE="car.color.size";
    public static final String VALIDATION_SEATS_MIN="car.seats.min";
    public static final String VALIDATION_SEATS_MAX="car.seats.max";
    public static final String VALIDATION_BRAND="car.brand";
    public static final String VALIDATION_BRAND_SIZE="car.brand.size";
    public static final String VALIDATION_MODEL="car.model";
    public static final String VALIDATION_MODEL_SIZE="car.model.size";
    public static final String VALIDATION_CATEGORY="car.category";
    public static final String VALIDATION_CATEGORY_SIZE="car.category.size";

    public static final String VALIDATION_FIRSTNAME="driver.firstname";
    public static final String VALIDATION_FIRSTNAME_SIZE="driver.firstname.size";
    public static final String VALIDATION_LASTNAME="driver.lastname";
    public static final String VALIDATION_LASTNAME_SIZE="driver.lastname.size";
    public static final String VALIDATION_PHONE="driver.phone";
    public static final String VALIDATION_PHONE_REGEX="^\\+?[1-9]\\d{1,14}$";
    public static final String VALIDATION_PHONE_REGEX_MESSAGE="driver.phone.regex.message";

    public static final String VALIDATION_PAGE_NUMBER="page.number";
    public static final String VALIDATION_PAGE_SIZE="page.size";



}
