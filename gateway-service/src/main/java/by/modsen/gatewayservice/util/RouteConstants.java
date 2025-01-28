package by.modsen.gatewayservice.util;

import java.util.List;

public final class RouteConstants {

    public static final List<String> PUBLIC_ROUTES = List.of(
            "/api/v1/auth/**"
    );

    public static final List<String> PASSENGER_ROUTES = List.of(
            "/api/v1/passengers/**"
    );

    public static final List<String> DRIVER_ROUTES = List.of(
            "/api/v1/drivers/**",
            "/api/v1/cars/**"
    );
}