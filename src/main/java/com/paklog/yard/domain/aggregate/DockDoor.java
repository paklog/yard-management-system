package com.paklog.yard.domain.aggregate;

import com.paklog.yard.domain.valueobject.*;
import com.paklog.yard.domain.event.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.*;

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
    public List<DomainEvent> domainEvents() { return getDomainEvents(); }
    public void clearDomainEvents() { domainEvents.clear(); }


    // Getters
    public String getId() { return id; }
    public String getDockNumber() { return dockNumber; }
    public String getDockName() { return dockName; }
    public DockStatus getStatus() { return status; }
    public LocationType getDockType() { return dockType; }
    public String getCurrentTrailerId() { return currentTrailerId; }
    public String getCurrentAppointmentId() { return currentAppointmentId; }
    public Integer getCapacity() { return capacity; }
    public Map<String, Object> getEquipment() { return equipment; }
    public Map<String, Object> getCapabilities() { return capabilities; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setDockNumber(String dockNumber) { this.dockNumber = dockNumber; }
    public void setDockName(String dockName) { this.dockName = dockName; }
    public void setStatus(DockStatus status) { this.status = status; }
    public void setDockType(LocationType dockType) { this.dockType = dockType; }
    public void setCurrentTrailerId(String currentTrailerId) { this.currentTrailerId = currentTrailerId; }
    public void setCurrentAppointmentId(String currentAppointmentId) { this.currentAppointmentId = currentAppointmentId; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public void setEquipment(Map<String, Object> equipment) { this.equipment = equipment; }
    public void setCapabilities(Map<String, Object> capabilities) { this.capabilities = capabilities; }
    public void setVersion(Long version) { this.version = version; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
