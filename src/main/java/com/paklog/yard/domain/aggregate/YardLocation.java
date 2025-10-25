package com.paklog.yard.domain.aggregate;

import com.paklog.yard.domain.valueobject.*;
import com.paklog.yard.domain.event.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.*;

@Document(collection = "yard_locations")
public class YardLocation {
    
    @Id
    private String id;
    
    private String locationCode;
    private String locationName;
    private LocationType locationType;
    private String status;
    
    private GPSCoordinates coordinates;
    private Map<String, Object> dimensions;
    
    private String currentTrailerId;
    private Integer capacity;
    private Integer currentOccupancy;
    
    private Map<String, Object> attributes;
    
    @Version
    private Long version;
    
    @CreatedDate
    private Instant createdAt;
    
    @LastModifiedDate
    private Instant updatedAt;
    
    @Transient
    private List<DomainEvent> domainEvents = new ArrayList<>();
    
    public boolean isAvailable() {
        return "AVAILABLE".equals(status) && currentTrailerId == null;
    }
    
    public void occupy(String trailerId) {
        if (!isAvailable()) {
            throw new IllegalStateException("Location is not available");
        }
        this.currentTrailerId = trailerId;
        this.status = "OCCUPIED";
        this.currentOccupancy = (this.currentOccupancy == null ? 0 : this.currentOccupancy) + 1;
    }
    
    public void vacate() {
        this.currentTrailerId = null;
        this.status = "AVAILABLE";
    }
    
    public List<DomainEvent> getDomainEvents() { return new ArrayList<>(domainEvents); }
    public void clearDomainEvents() { domainEvents.clear(); }


    // Getters
    public String getId() { return id; }
    public String getLocationCode() { return locationCode; }
    public String getLocationName() { return locationName; }
    public LocationType getLocationType() { return locationType; }
    public String getStatus() { return status; }
    public GPSCoordinates getCoordinates() { return coordinates; }
    public Map<String, Object> getDimensions() { return dimensions; }
    public String getCurrentTrailerId() { return currentTrailerId; }
    public Integer getCapacity() { return capacity; }
    public Integer getCurrentOccupancy() { return currentOccupancy; }
    public Map<String, Object> getAttributes() { return attributes; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public void setLocationType(LocationType locationType) { this.locationType = locationType; }
    public void setStatus(String status) { this.status = status; }
    public void setCoordinates(GPSCoordinates coordinates) { this.coordinates = coordinates; }
    public void setDimensions(Map<String, Object> dimensions) { this.dimensions = dimensions; }
    public void setCurrentTrailerId(String currentTrailerId) { this.currentTrailerId = currentTrailerId; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public void setCurrentOccupancy(Integer currentOccupancy) { this.currentOccupancy = currentOccupancy; }
    public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }
    public void setVersion(Long version) { this.version = version; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
