package com.paklog.yard.infrastructure.web.controller;

import com.paklog.yard.application.command.ScheduleAppointmentCommand;
import com.paklog.yard.application.port.in.YardManagementUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Appointment scheduling")
public class AppointmentController {
    
    private final YardManagementUseCase yardUseCase;
    
    @PostMapping
    @Operation(summary = "Schedule new appointment")
    public ResponseEntity<String> scheduleAppointment(@Valid @RequestBody ScheduleAppointmentCommand command) {
        log.info("REST: Scheduling appointment");
        String appointmentId = yardUseCase.scheduleAppointment(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentId);
    }
}
