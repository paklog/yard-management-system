package com.paklog.yard.application.command;

import com.paklog.yard.domain.valueobject.GPSCoordinates;
import lombok.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInTrailerCommand {
    @NotBlank
    private String trailerNumber;
    private String driverId;
    private String driverName;
    private String appointmentId;
    private GPSCoordinates gateLocation;
}
