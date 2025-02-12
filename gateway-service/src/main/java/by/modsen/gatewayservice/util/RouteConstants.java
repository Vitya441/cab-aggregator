package by.modsen.gatewayservice.util;

import java.util.List;

public final class RouteConstants {

    public static final List<String> PUBLIC_ROUTES = List.of(
            "/api/v1/auth/**"
    );

    public static final List<String> PASSENGER_ROUTES = List.of(
            "/api/v1/passengers/**",
            "/api/v1/rides",
            "/api/v1/rides/current",
            "/api/v1/rides/passenger/history/**",
            "/api/v1/rides/{rideId}/rate-driver"
    );

    public static final List<String> DRIVER_ROUTES = List.of(
            "/api/v1/drivers/**",
            "/api/v1/cars/**",
            "/api/v1/rides/driver/history/**",
            "/api/v1/rides/{rideId}/confirm",
            "/api/v1/rides/{rideId}/reject",
            "/api/v1/rides/{rideId}/start",
            "/api/v1/rides/{rideId}/end",
            "/api/v1/rides/{rideId}/rate-passenger"
    );
}