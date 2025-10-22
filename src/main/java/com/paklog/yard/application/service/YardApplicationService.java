package com.paklog.yard.application.service;

import com.paklog.yard.application.command.*;
import com.paklog.yard.application.port.in.*;
import com.paklog.yard.application.port.out.PublishEventPort;
import com.paklog.yard.domain.aggregate.*;
import com.paklog.yard.domain.event.DomainEvent;
import com.paklog.yard.domain.repository.*;
import com.paklog.yard.domain.service.*;
import com.paklog.yard.domain.valueobject.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class YardApplicationService implements YardManagementUseCase {
    
    private final TrailerRepository trailerRepository;
    private final DockDoorRepository dockRepository;
    private final AppointmentRepository appointmentRepository;
    private final YardLocationRepository locationRepository;
    private final DockSchedulingService schedulingService;
    private final TrailerTrackingService trackingService;
    private final PublishEventPort publishEventPort;
    
    @Override
    @Transactional
    public String checkInTrailer(CheckInTrailerCommand command) {
        log.info("Checking in trailer: {}", command.getTrailerNumber());
        
        Trailer trailer = trailerRepository.findByTrailerNumber(command.getTrailerNumber())
            .orElse(Trailer.builder()
                .id(UUID.randomUUID().toString())
                .trailerNumber(command.getTrailerNumber())
                .status(TrailerStatus.EXPECTED)
                .build());
        
        trailer.setDriverId(command.getDriverId());
        trailer.setDriverName(command.getDriverName());
        trailer.setAppointmentId(command.getAppointmentId());
        trailer.checkIn(command.getGateLocation());
        
        Trailer saved = trailerRepository.save(trailer);
        publishDomainEvents(saved);
        
        return saved.getId();
    }
    
    @Override
    @Transactional
    public void assignDock(AssignDockCommand command) {
        log.info("Assigning dock for trailer: {}", command.getTrailerId());
        
        Trailer trailer = trailerRepository.findById(command.getTrailerId())
            .orElseThrow(() -> new IllegalArgumentException("Trailer not found"));
        
        String dockId = command.getDockId();
        if (dockId == null) {
            List<DockDoor> availableDocks = dockRepository.findAvailableDocks();
            DockDoor dock = schedulingService.findAvailableDock(availableDocks, null);
            if (dock == null) {
                throw new IllegalStateException("No available docks");
            }
            dockId = dock.getId();
        }
        
        DockDoor dock = dockRepository.findById(dockId)
            .orElseThrow(() -> new IllegalArgumentException("Dock not found"));
        
        dock.assignTrailer(trailer.getId(), trailer.getAppointmentId());
        trailer.assignToDock(dockId);
        
        trailerRepository.save(trailer);
        dockRepository.save(dock);
        
        publishDomainEvents(trailer);
        publishDomainEvents(dock);
    }
    
    @Override
    @Transactional
    public String scheduleAppointment(ScheduleAppointmentCommand command) {
        log.info("Scheduling appointment for carrier: {}", command.getCarrierId());
        
        Appointment appointment = Appointment.builder()
            .id(UUID.randomUUID().toString())
            .appointmentNumber("APT-" + System.currentTimeMillis())
            .status(AppointmentStatus.SCHEDULED)
            .carrierId(command.getCarrierId())
            .trailerId(command.getTrailerId())
            .appointmentType(command.getAppointmentType())
            .referenceNumber(command.getReferenceNumber())
            .cargo(command.getCargo())
            .build();
        
        List<DockDoor> availableDocks = dockRepository.findAvailableDocks();
        DockDoor dock = schedulingService.findAvailableDock(availableDocks, null);
        
        if (dock != null) {
            appointment.schedule(dock.getId(), command.getWindow());
            dock.reserve(appointment.getId());
            dockRepository.save(dock);
        }
        
        Appointment saved = appointmentRepository.save(appointment);
        publishDomainEvents(saved);
        
        return saved.getId();
    }
    
    @Override
    @Transactional
    public void checkOutTrailer(CheckOutTrailerCommand command) {
        log.info("Checking out trailer: {}", command.getTrailerId());
        
        Trailer trailer = trailerRepository.findById(command.getTrailerId())
            .orElseThrow(() -> new IllegalArgumentException("Trailer not found"));
        
        if (trailer.getAssignedDockId() != null) {
            DockDoor dock = dockRepository.findById(trailer.getAssignedDockId()).orElse(null);
            if (dock != null) {
                dock.releaseTrailer();
                dockRepository.save(dock);
            }
        }
        
        trailer.checkOut();
        Trailer saved = trailerRepository.save(trailer);
        publishDomainEvents(saved);
    }
    
    @Override
    public Trailer getTrailer(String trailerId) {
        return trailerRepository.findById(trailerId)
            .orElseThrow(() -> new IllegalArgumentException("Trailer not found"));
    }
    
    @Override
    public List<Trailer> getActiveTrailers() {
        return trailerRepository.findActiveTrailers();
    }
    
    private void publishDomainEvents(Object aggregate) {
        List<DomainEvent> events = null;
        
        if (aggregate instanceof Trailer) {
            events = ((Trailer) aggregate).getDomainEvents();
            ((Trailer) aggregate).clearDomainEvents();
        } else if (aggregate instanceof DockDoor) {
            events = ((DockDoor) aggregate).getDomainEvents();
            ((DockDoor) aggregate).clearDomainEvents();
        } else if (aggregate instanceof Appointment) {
            events = ((Appointment) aggregate).getDomainEvents();
            ((Appointment) aggregate).clearDomainEvents();
        }
        
        if (events != null) {
            events.forEach(event -> {
                try {
                    publishEventPort.publishEvent(event);
                } catch (Exception e) {
                    log.error("Failed to publish event: {}", event.getEventType(), e);
                }
            });
        }
    }
}
