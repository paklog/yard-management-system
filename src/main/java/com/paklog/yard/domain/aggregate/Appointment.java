package com.paklog.yard.domain.aggregate;

import com.paklog.yard.domain.valueobject.*;
import com.paklog.yard.domain.event.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.*;

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
    public List<DomainEvent> domainEvents() { return getDomainEvents(); }
    public void clearDomainEvents() { domainEvents.clear(); }


    // Getters
    public String getId() { return id; }
    public String getAppointmentNumber() { return appointmentNumber; }
    public AppointmentStatus getStatus() { return status; }
    public String getAppointmentType() { return appointmentType; }
    public String getTrailerId() { return trailerId; }
    public String getCarrierId() { return carrierId; }
    public String getDockId() { return dockId; }
    public AppointmentWindow getWindow() { return window; }
    public Instant getActualArrivalTime() { return actualArrivalTime; }
    public Instant getActualDepartureTime() { return actualDepartureTime; }
    public String getReferenceNumber() { return referenceNumber; }
    public Map<String, Object> getCargo() { return cargo; }
    public String getSpecialInstructions() { return specialInstructions; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setAppointmentNumber(String appointmentNumber) { this.appointmentNumber = appointmentNumber; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public void setAppointmentType(String appointmentType) { this.appointmentType = appointmentType; }
    public void setTrailerId(String trailerId) { this.trailerId = trailerId; }
    public void setCarrierId(String carrierId) { this.carrierId = carrierId; }
    public void setDockId(String dockId) { this.dockId = dockId; }
    public void setWindow(AppointmentWindow window) { this.window = window; }
    public void setActualArrivalTime(Instant actualArrivalTime) { this.actualArrivalTime = actualArrivalTime; }
    public void setActualDepartureTime(Instant actualDepartureTime) { this.actualDepartureTime = actualDepartureTime; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }
    public void setCargo(Map<String, Object> cargo) { this.cargo = cargo; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }
    public void setVersion(Long version) { this.version = version; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    // Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
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

        public Builder id(String id) { this.id = id; return this; }
        public Builder appointmentNumber(String appointmentNumber) { this.appointmentNumber = appointmentNumber; return this; }
        public Builder status(AppointmentStatus status) { this.status = status; return this; }
        public Builder appointmentType(String appointmentType) { this.appointmentType = appointmentType; return this; }
        public Builder trailerId(String trailerId) { this.trailerId = trailerId; return this; }
        public Builder carrierId(String carrierId) { this.carrierId = carrierId; return this; }
        public Builder dockId(String dockId) { this.dockId = dockId; return this; }
        public Builder window(AppointmentWindow window) { this.window = window; return this; }
        public Builder actualArrivalTime(Instant actualArrivalTime) { this.actualArrivalTime = actualArrivalTime; return this; }
        public Builder actualDepartureTime(Instant actualDepartureTime) { this.actualDepartureTime = actualDepartureTime; return this; }
        public Builder referenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; return this; }
        public Builder cargo(Map<String, Object> cargo) { this.cargo = cargo; return this; }
        public Builder specialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; return this; }

        public Appointment build() {
            Appointment obj = new Appointment();
            obj.id = this.id;
            obj.appointmentNumber = this.appointmentNumber;
            obj.status = this.status;
            obj.appointmentType = this.appointmentType;
            obj.trailerId = this.trailerId;
            obj.carrierId = this.carrierId;
            obj.dockId = this.dockId;
            obj.window = this.window;
            obj.actualArrivalTime = this.actualArrivalTime;
            obj.actualDepartureTime = this.actualDepartureTime;
            obj.referenceNumber = this.referenceNumber;
            obj.cargo = this.cargo;
            obj.specialInstructions = this.specialInstructions;
            return obj;
        

}
}
}
