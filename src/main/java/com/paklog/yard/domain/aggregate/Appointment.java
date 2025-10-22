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
@Document(collection = "appointments")
public class Appointment {
    
    @Id
    private String id;
    
    private String appointmentNumber;
    private AppointmentStatus status;
    private String appointmentType;
    
    private String trailerId;
    private String carrierId;
    private String dockId;
    
    private AppointmentWindow window;
    private Instant actualArrivalTime;
    private Instant actualDepartureTime;
    
    private String referenceNumber;
    private Map<String, Object> cargo;
    private String specialInstructions;
    
    @Version
    private Long version;
    
    @CreatedDate
    private Instant createdAt;
    
    @LastModifiedDate
    private Instant updatedAt;
    
    @Transient
    private List<DomainEvent> domainEvents = new ArrayList<>();
    
    public void schedule(String dockId, AppointmentWindow window) {
        this.dockId = dockId;
        this.window = window;
        this.status = AppointmentStatus.SCHEDULED;
        
        addDomainEvent(AppointmentScheduledEvent.builder()
            .appointmentId(this.id)
            .trailerId(this.trailerId)
            .dockId(dockId)
            .scheduledTime(window.getStartTime())
            .build());
    }
    
    public void confirm() {
        this.status = AppointmentStatus.CONFIRMED;
    }
    
    public void checkIn(Instant arrivalTime) {
        this.actualArrivalTime = arrivalTime;
        this.status = AppointmentStatus.CHECKED_IN;
    }
    
    public void start() {
        this.status = AppointmentStatus.IN_PROGRESS;
    }
    
    public void complete(Instant departureTime) {
        this.actualDepartureTime = departureTime;
        this.status = AppointmentStatus.COMPLETED;
    }
    
    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }
    
    public boolean isLate() {
        if (actualArrivalTime == null || window == null) return false;
        return actualArrivalTime.isAfter(window.getEndTime());
    }
    
    private void addDomainEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }
    
    public List<DomainEvent> getDomainEvents() { return new ArrayList<>(domainEvents); }
    public void clearDomainEvents() { domainEvents.clear(); }
}
