package by.modsen.ridesservice.util;

import by.modsen.commonmodule.enumeration.RideStatus;

import java.util.EnumSet;
import java.util.Set;

public final class RideStatusConstants {

    public static final Set<RideStatus> ACTIVE_STATUSES = EnumSet.of(
            RideStatus.ACCEPTED,
            RideStatus.IN_PROGRESS
    );

    private RideStatusConstants() {}
}