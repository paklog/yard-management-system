package com.paklog.yard.application.command;

import com.paklog.yard.domain.valueobject.AppointmentWindow;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record ScheduleAppointmentCommand(
    @NotBlank
    String carrierId,
    String trailerId,
    @NotNull
    AppointmentWindow window,
    String appointmentType,
    String referenceNumber,
    Map<String, Object> cargo
) {}
