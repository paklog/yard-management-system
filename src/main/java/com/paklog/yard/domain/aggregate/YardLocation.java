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
}
