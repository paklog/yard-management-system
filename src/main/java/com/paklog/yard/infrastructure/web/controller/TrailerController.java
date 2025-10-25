package com.paklog.yard.infrastructure.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paklog.yard.application.command.*;
import com.paklog.yard.application.port.in.YardManagementUseCase;
import com.paklog.yard.domain.aggregate.Trailer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trailers")
@Tag(name = "Trailers", description = "Trailer management endpoints")
public class TrailerController {
    private static final Logger log = LoggerFactory.getLogger(TrailerController.class);

    
    private final YardManagementUseCase yardUseCase;
    public TrailerController(YardManagementUseCase yardUseCase) {
        this.yardUseCase = yardUseCase;
    }

    
    @PostMapping("/check-in")
    @Operation(summary = "Check in trailer at gate")
    public ResponseEntity<String> checkInTrailer(@Valid @RequestBody CheckInTrailerCommand command) {
        log.info("REST: Checking in trailer: {}", command.trailerNumber());
        String trailerId = yardUseCase.checkInTrailer(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(trailerId);
    }
    
    @PostMapping("/{id}/assign-dock")
    @Operation(summary = "Assign dock to trailer")
    public ResponseEntity<Void> assignDock(@PathVariable String id, @RequestParam(required = false) String dockId) {
        log.info("REST: Assigning dock for trailer: {}", id);
        AssignDockCommand command = new AssignDockCommand(id, dockId, "system");
        yardUseCase.assignDock(command);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/check-out")
    @Operation(summary = "Check out trailer")
    public ResponseEntity<Void> checkOutTrailer(@PathVariable String id) {
        log.info("REST: Checking out trailer: {}", id);
        CheckOutTrailerCommand command = new CheckOutTrailerCommand(id, "system");
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
