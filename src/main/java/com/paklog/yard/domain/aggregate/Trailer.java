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
@Document(collection = "trailers")
public class Trailer {
    
    @Id
    private String id;
    
    private String trailerNumber;
    private String licensePlate;
    private TrailerStatus status;
    
    private String carrierId;
    private String carrierName;
    private String driverId;
    private String driverName;
    
    private GPSCoordinates currentLocation;
    private String currentYardLocationId;
    private String assignedDockId;
    
    private Instant arrivedAt;
    private Instant departedAt;
    private Long dwellTimeMinutes;
    
    private String appointmentId;
    private Map<String, Object> cargo;
    private Map<String, Object> metadata;
    
    @Version
    private Long version;
    
    @CreatedDate
    private Instant createdAt;
    
    @LastModifiedDate
    private Instant updatedAt;
    
    @Transient
    private List<DomainEvent> domainEvents = new ArrayList<>();
    
    public void checkIn(GPSCoordinates gateLocation) {
        this.status = TrailerStatus.CHECKED_IN;
        this.arrivedAt = Instant.now();
        this.currentLocation = gateLocation;
        
        addDomainEvent(TrailerArrivedEvent.builder()
            .trailerId(this.id)
            .trailerNumber(this.trailerNumber)
            .arrivedAt(this.arrivedAt)
            .build());
    }
    
    public void assignToDock(String dockId) {
        this.assignedDockId = dockId;
        this.status = TrailerStatus.AT_DOCK;
        
        addDomainEvent(DockAssignedEvent.builder()
            .trailerId(this.id)
            .dockId(dockId)
            .assignedAt(Instant.now())
            .build());
    }
    
    public void startLoading() {
        this.status = TrailerStatus.LOADING;
    }
    
    public void completeLoading() {
        this.status = TrailerStatus.LOADED;
        
        addDomainEvent(LoadingCompletedEvent.builder()
            .trailerId(this.id)
            .completedAt(Instant.now())
            .build());
    }
    
    public void checkOut() {
        this.status = TrailerStatus.CHECKED_OUT;
        this.departedAt = Instant.now();
        
        if (this.arrivedAt != null) {
            this.dwellTimeMinutes = (departedAt.toEpochMilli() - arrivedAt.toEpochMilli()) / 60000;
        }
        
        addDomainEvent(TrailerDepartedEvent.builder()
            .trailerId(this.id)
            .departedAt(this.departedAt)
            .dwellTimeMinutes(this.dwellTimeMinutes)
            .build());
    }
    
    public void updateLocation(GPSCoordinates newLocation) {
        this.currentLocation = newLocation;
    }
    
    private void addDomainEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }
    
    public List<DomainEvent> getDomainEvents() { return new ArrayList<>(domainEvents); }
    public void clearDomainEvents() { domainEvents.clear(); }
}
