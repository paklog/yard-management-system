package com.paklog.yard.domain.aggregate;

import com.paklog.yard.domain.valueobject.*;
import com.paklog.yard.domain.event.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.*;

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
    public List<DomainEvent> domainEvents() { return getDomainEvents(); }
    public void clearDomainEvents() { domainEvents.clear(); }


    // Getters
    public String getId() { return id; }
    public String getTrailerNumber() { return trailerNumber; }
    public String getLicensePlate() { return licensePlate; }
    public TrailerStatus getStatus() { return status; }
    public String getCarrierId() { return carrierId; }
    public String getCarrierName() { return carrierName; }
    public String getDriverId() { return driverId; }
    public String getDriverName() { return driverName; }
    public GPSCoordinates getCurrentLocation() { return currentLocation; }
    public String getCurrentYardLocationId() { return currentYardLocationId; }
    public String getAssignedDockId() { return assignedDockId; }
    public Instant getArrivedAt() { return arrivedAt; }
    public Instant getDepartedAt() { return departedAt; }
    public Long getDwellTimeMinutes() { return dwellTimeMinutes; }
    public String getAppointmentId() { return appointmentId; }
    public Map<String, Object> getCargo() { return cargo; }
    public Map<String, Object> getMetadata() { return metadata; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTrailerNumber(String trailerNumber) { this.trailerNumber = trailerNumber; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public void setStatus(TrailerStatus status) { this.status = status; }
    public void setCarrierId(String carrierId) { this.carrierId = carrierId; }
    public void setCarrierName(String carrierName) { this.carrierName = carrierName; }
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public void setCurrentLocation(GPSCoordinates currentLocation) { this.currentLocation = currentLocation; }
    public void setCurrentYardLocationId(String currentYardLocationId) { this.currentYardLocationId = currentYardLocationId; }
    public void setAssignedDockId(String assignedDockId) { this.assignedDockId = assignedDockId; }
    public void setArrivedAt(Instant arrivedAt) { this.arrivedAt = arrivedAt; }
    public void setDepartedAt(Instant departedAt) { this.departedAt = departedAt; }
    public void setDwellTimeMinutes(Long dwellTimeMinutes) { this.dwellTimeMinutes = dwellTimeMinutes; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public void setCargo(Map<String, Object> cargo) { this.cargo = cargo; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    public void setVersion(Long version) { this.version = version; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
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
        private Long version;

        public Builder id(String id) { this.id = id; return this; }
        public Builder trailerNumber(String trailerNumber) { this.trailerNumber = trailerNumber; return this; }
        public Builder licensePlate(String licensePlate) { this.licensePlate = licensePlate; return this; }
        public Builder status(TrailerStatus status) { this.status = status; return this; }
        public Builder carrierId(String carrierId) { this.carrierId = carrierId; return this; }
        public Builder carrierName(String carrierName) { this.carrierName = carrierName; return this; }
        public Builder driverId(String driverId) { this.driverId = driverId; return this; }
        public Builder driverName(String driverName) { this.driverName = driverName; return this; }
        public Builder currentLocation(GPSCoordinates currentLocation) { this.currentLocation = currentLocation; return this; }
        public Builder currentYardLocationId(String currentYardLocationId) { this.currentYardLocationId = currentYardLocationId; return this; }
        public Builder assignedDockId(String assignedDockId) { this.assignedDockId = assignedDockId; return this; }
        public Builder arrivedAt(Instant arrivedAt) { this.arrivedAt = arrivedAt; return this; }
        public Builder departedAt(Instant departedAt) { this.departedAt = departedAt; return this; }
        public Builder dwellTimeMinutes(Long dwellTimeMinutes) { this.dwellTimeMinutes = dwellTimeMinutes; return this; }
        public Builder appointmentId(String appointmentId) { this.appointmentId = appointmentId; return this; }
        public Builder cargo(Map<String, Object> cargo) { this.cargo = cargo; return this; }
        public Builder metadata(Map<String, Object> metadata) { this.metadata = metadata; return this; }
        public Builder version(Long version) { this.version = version; return this; }
        public Builder createdAt(Instant createdAt) { return this; }
        public Builder updatedAt(Instant updatedAt) { return this; }

        public Trailer build() {
            Trailer obj = new Trailer();
            obj.id = this.id;
            obj.trailerNumber = this.trailerNumber;
            obj.licensePlate = this.licensePlate;
            obj.status = this.status;
            obj.carrierId = this.carrierId;
            obj.carrierName = this.carrierName;
            obj.driverId = this.driverId;
            obj.driverName = this.driverName;
            obj.currentLocation = this.currentLocation;
            obj.currentYardLocationId = this.currentYardLocationId;
            obj.assignedDockId = this.assignedDockId;
            obj.arrivedAt = this.arrivedAt;
            obj.departedAt = this.departedAt;
            obj.dwellTimeMinutes = this.dwellTimeMinutes;
            obj.appointmentId = this.appointmentId;
            obj.cargo = this.cargo;
            obj.metadata = this.metadata;
            obj.version = this.version;
            return obj;
        

}
}
}
