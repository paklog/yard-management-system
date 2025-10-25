package com.paklog.yard.application.command;

import com.paklog.yard.domain.valueobject.GPSCoordinates;
import jakarta.validation.constraints.NotBlank;

public record CheckInTrailerCommand(
    @NotBlank
    String trailerNumber,
    String driverId,
    String driverName,
    String appointmentId,
    GPSCoordinates gateLocation
) {}
