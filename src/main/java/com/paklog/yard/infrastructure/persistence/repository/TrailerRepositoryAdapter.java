package com.paklog.yard.infrastructure.persistence.repository;

import com.paklog.yard.domain.aggregate.Trailer;
import com.paklog.yard.domain.repository.TrailerRepository;
import com.paklog.yard.domain.valueobject.TrailerStatus;
import com.paklog.yard.infrastructure.persistence.document.TrailerDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrailerRepositoryAdapter implements TrailerRepository {
    
    private final MongoTrailerRepository mongoRepository;
    
    @Override
    public Trailer save(Trailer trailer) {
        TrailerDocument doc = toDocument(trailer);
        TrailerDocument saved = mongoRepository.save(doc);
        return toDomain(saved);
    }
    
    @Override
    public Optional<Trailer> findById(String id) {
        return mongoRepository.findById(id).map(this::toDomain);
    }
    
    @Override
    public Optional<Trailer> findByTrailerNumber(String trailerNumber) {
        return mongoRepository.findByTrailerNumber(trailerNumber).map(this::toDomain);
    }
    
    @Override
    public List<Trailer> findAll() {
        return mongoRepository.findAll().stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Trailer> findByStatus(TrailerStatus status) {
        return mongoRepository.findByStatus(status.name()).stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Trailer> findByCarrierId(String carrierId) {
        return mongoRepository.findByCarrierId(carrierId).stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Trailer> findActiveTrailers() {
        return mongoRepository.findAll().stream()
            .map(this::toDomain)
            .filter(t -> t.getStatus() != TrailerStatus.DEPARTED)
            .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(String id) {
        mongoRepository.deleteById(id);
    }
    
    private TrailerDocument toDocument(Trailer trailer) {
        return TrailerDocument.builder()
            .id(trailer.getId())
            .trailerNumber(trailer.getTrailerNumber())
            .licensePlate(trailer.getLicensePlate())
            .status(trailer.getStatus() != null ? trailer.getStatus().name() : null)
            .carrierId(trailer.getCarrierId())
            .carrierName(trailer.getCarrierName())
            .driverId(trailer.getDriverId())
            .driverName(trailer.getDriverName())
            .currentYardLocationId(trailer.getCurrentYardLocationId())
            .assignedDockId(trailer.getAssignedDockId())
            .arrivedAt(trailer.getArrivedAt())
            .departedAt(trailer.getDepartedAt())
            .dwellTimeMinutes(trailer.getDwellTimeMinutes())
            .appointmentId(trailer.getAppointmentId())
            .cargo(trailer.getCargo())
            .metadata(trailer.getMetadata())
            .version(trailer.getVersion())
            .createdAt(trailer.getCreatedAt())
            .updatedAt(trailer.getUpdatedAt())
            .build();
    }
    
    private Trailer toDomain(TrailerDocument doc) {
        return Trailer.builder()
            .id(doc.getId())
            .trailerNumber(doc.getTrailerNumber())
            .licensePlate(doc.getLicensePlate())
            .status(doc.getStatus() != null ? TrailerStatus.valueOf(doc.getStatus()) : null)
            .carrierId(doc.getCarrierId())
            .carrierName(doc.getCarrierName())
            .driverId(doc.getDriverId())
            .driverName(doc.getDriverName())
            .currentYardLocationId(doc.getCurrentYardLocationId())
            .assignedDockId(doc.getAssignedDockId())
            .arrivedAt(doc.getArrivedAt())
            .departedAt(doc.getDepartedAt())
            .dwellTimeMinutes(doc.getDwellTimeMinutes())
            .appointmentId(doc.getAppointmentId())
            .cargo(doc.getCargo())
            .metadata(doc.getMetadata())
            .version(doc.getVersion())
            .createdAt(doc.getCreatedAt())
            .updatedAt(doc.getUpdatedAt())
            .build();
    }
}
