package com.paklog.yard.domain.aggregate;

import com.paklog.yard.domain.valueobject.*;
import com.paklog.yard.domain.event.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "dock_doors")
public class DockDoor {
    
    @Id
    private String id;
    
    private String dockNumber;
    private String dockName;
    private DockStatus status;
    private LocationType dockType;
    
    private String currentTrailerId;
    private String currentAppointmentId;
    
    private Integer capacity;
    private Map<String, Object> equipment;
    private Map<String, Object> capabilities;
    
    @Version
    private Long version;
    
    @CreatedDate
    private Instant createdAt;
    
    @LastModifiedDate
    private Instant updatedAt;
    
    @Transient
    private List<DomainEvent> domainEvents = new ArrayList<>();
    
    public boolean isAvailable() {
        return status == DockStatus.AVAILABLE && currentTrailerId == null;
    }
    
    public void assignTrailer(String trailerId, String appointmentId) {
        if (!isAvailable()) {
            throw new IllegalStateException("Dock is not available");
        }
        this.currentTrailerId = trailerId;
        this.currentAppointmentId = appointmentId;
        this.status = DockStatus.OCCUPIED;
    }
    
    public void releaseTrailer() {
        this.currentTrailerId = null;
        this.currentAppointmentId = null;
        this.status = DockStatus.AVAILABLE;
    }
    
    public void reserve(String appointmentId) {
        if (status != DockStatus.AVAILABLE) {
            throw new IllegalStateException("Dock cannot be reserved");
        }
        this.currentAppointmentId = appointmentId;
        this.status = DockStatus.RESERVED;
    }
    
    public List<DomainEvent> getDomainEvents() { return new ArrayList<>(domainEvents); }
    public void clearDomainEvents() { domainEvents.clear(); }
}
