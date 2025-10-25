package com.paklog.yard.infrastructure.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paklog.yard.application.command.ScheduleAppointmentCommand;
import com.paklog.yard.application.port.in.YardManagementUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/appointments")
@Tag(name = "Appointments", description = "Appointment scheduling")
public class AppointmentController {
    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);

    
    private final YardManagementUseCase yardUseCase;
    public AppointmentController(YardManagementUseCase yardUseCase) {
        this.yardUseCase = yardUseCase;
    }

    
    @PostMapping
    @Operation(summary = "Schedule new appointment")
    public ResponseEntity<String> scheduleAppointment(@Valid @RequestBody ScheduleAppointmentCommand command) {
        log.info("REST: Scheduling appointment");
        String appointmentId = yardUseCase.scheduleAppointment(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentId);
    }
}
