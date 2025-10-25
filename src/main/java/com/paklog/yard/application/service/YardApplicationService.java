package com.paklog.yard.application.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paklog.yard.application.command.*;
import com.paklog.yard.application.port.in.*;
import com.paklog.yard.application.port.out.PublishEventPort;
import com.paklog.yard.domain.aggregate.*;
import com.paklog.yard.domain.event.DomainEvent;
import com.paklog.yard.domain.repository.*;
import com.paklog.yard.domain.service.*;
import com.paklog.yard.domain.valueobject.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.*;

@Service
public class YardApplicationService implements YardManagementUseCase {
    private static final Logger log = LoggerFactory.getLogger(YardApplicationService.class);

    
    private final TrailerRepository trailerRepository;
    private final DockDoorRepository dockRepository;
    private final AppointmentRepository appointmentRepository;
    private final YardLocationRepository locationRepository;
    private final DockSchedulingService schedulingService;
    private final TrailerTrackingService trackingService;
    private final PublishEventPort publishEventPort;
    public YardApplicationService(TrailerRepository trailerRepository, DockDoorRepository dockRepository, AppointmentRepository appointmentRepository, YardLocationRepository locationRepository, DockSchedulingService schedulingService, TrailerTrackingService trackingService, PublishEventPort publishEventPort) {
        this.trailerRepository = trailerRepository;
        this.dockRepository = dockRepository;
        this.appointmentRepository = appointmentRepository;
        this.locationRepository = locationRepository;
        this.schedulingService = schedulingService;
        this.trackingService = trackingService;
        this.publishEventPort = publishEventPort;
    }

    
    @Override
    @Transactional
    public String checkInTrailer(CheckInTrailerCommand command) {
        log.info("Checking in trailer: {}", command.trailerNumber());

        Trailer trailer = trailerRepository.findByTrailerNumber(command.trailerNumber())
            .orElse(Trailer.builder()
                .id(UUID.randomUUID().toString())
                .trailerNumber(command.trailerNumber())
                .status(TrailerStatus.EXPECTED)
                .build());

        trailer.setDriverId(command.driverId());
        trailer.setDriverName(command.driverName());
        trailer.setAppointmentId(command.appointmentId());
        trailer.checkIn(command.gateLocation());

        Trailer saved = trailerRepository.save(trailer);
        publishDomainEvents(saved);

        return saved.getId();
    }
    
    @Override
    @Transactional
    public void assignDock(AssignDockCommand command) {
        log.info("Assigning dock for trailer: {}", command.trailerId());

        Trailer trailer = trailerRepository.findById(command.trailerId())
            .orElseThrow(() -> new IllegalArgumentException("Trailer not found"));

        String dockId = command.dockId();
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
        log.info("Scheduling appointment for carrier: {}", command.carrierId());

        Appointment appointment = Appointment.builder()
            .id(UUID.randomUUID().toString())
            .appointmentNumber("APT-" + System.currentTimeMillis())
            .status(AppointmentStatus.SCHEDULED)
            .carrierId(command.carrierId())
            .trailerId(command.trailerId())
            .appointmentType(command.appointmentType())
            .referenceNumber(command.referenceNumber())
            .cargo(command.cargo())
            .build();

        List<DockDoor> availableDocks = dockRepository.findAvailableDocks();
        DockDoor dock = schedulingService.findAvailableDock(availableDocks, null);

        if (dock != null) {
            appointment.schedule(dock.getId(), command.window());
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
        log.info("Checking out trailer: {}", command.trailerId());

        Trailer trailer = trailerRepository.findById(command.trailerId())
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
            events = ((Trailer) aggregate).domainEvents();
            ((Trailer) aggregate).clearDomainEvents();
        } else if (aggregate instanceof DockDoor) {
            events = ((DockDoor) aggregate).domainEvents();
            ((DockDoor) aggregate).clearDomainEvents();
        } else if (aggregate instanceof Appointment) {
            events = ((Appointment) aggregate).domainEvents();
            ((Appointment) aggregate).clearDomainEvents();
        }
        
        if (events != null) {
            events.forEach(event -> {
                try {
                    publishEventPort.publishEvent(event);
                } catch (Exception e) {
                    log.error("Failed to publish event: {}", event.eventType(), e);
                }
            });
        }
    }
}
