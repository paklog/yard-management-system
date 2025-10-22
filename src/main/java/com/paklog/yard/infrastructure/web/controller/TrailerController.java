package com.paklog.yard.infrastructure.web.controller;

import com.paklog.yard.application.command.*;
import com.paklog.yard.application.port.in.YardManagementUseCase;
import com.paklog.yard.domain.aggregate.Trailer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/trailers")
@RequiredArgsConstructor
@Tag(name = "Trailers", description = "Trailer management endpoints")
public class TrailerController {
    
    private final YardManagementUseCase yardUseCase;
    
    @PostMapping("/check-in")
    @Operation(summary = "Check in trailer at gate")
    public ResponseEntity<String> checkInTrailer(@Valid @RequestBody CheckInTrailerCommand command) {
        log.info("REST: Checking in trailer: {}", command.getTrailerNumber());
        String trailerId = yardUseCase.checkInTrailer(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(trailerId);
    }
    
    @PostMapping("/{id}/assign-dock")
    @Operation(summary = "Assign dock to trailer")
    public ResponseEntity<Void> assignDock(@PathVariable String id, @RequestParam(required = false) String dockId) {
        log.info("REST: Assigning dock for trailer: {}", id);
        AssignDockCommand command = AssignDockCommand.builder()
            .trailerId(id)
            .dockId(dockId)
            .build();
        yardUseCase.assignDock(command);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/check-out")
    @Operation(summary = "Check out trailer")
    public ResponseEntity<Void> checkOutTrailer(@PathVariable String id) {
        log.info("REST: Checking out trailer: {}", id);
        CheckOutTrailerCommand command = CheckOutTrailerCommand.builder()
            .trailerId(id)
            .build();
        yardUseCase.checkOutTrailer(command);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get trailer by ID")
    public ResponseEntity<Trailer> getTrailer(@PathVariable String id) {
        Trailer trailer = yardUseCase.getTrailer(id);
        return ResponseEntity.ok(trailer);
    }
    
    @GetMapping("/active")
    @Operation(summary = "Get all active trailers in yard")
    public ResponseEntity<List<Trailer>> getActiveTrailers() {
        List<Trailer> trailers = yardUseCase.getActiveTrailers();
        return ResponseEntity.ok(trailers);
    }
}
