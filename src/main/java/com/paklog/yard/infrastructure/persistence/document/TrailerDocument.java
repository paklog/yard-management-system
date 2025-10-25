package com.paklog.yard.infrastructure.persistence.document;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.Instant;
import java.util.Map;

@Document(collection = "trailers")
public class TrailerDocument {
    @Id
    private String id;
    @Indexed(unique = true)
    private String trailerNumber;
    private String licensePlate;
    @Indexed
    private String status;
    private String carrierId;
    private String carrierName;
    private String driverId;
    private String driverName;
    private Map<String, Object> currentLocation;
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


    // Getters
    public String getId() { return id; }
    public String getTrailerNumber() { return trailerNumber; }
    public String getLicensePlate() { return licensePlate; }
    public String getStatus() { return status; }
    public String getCarrierId() { return carrierId; }
    public String getCarrierName() { return carrierName; }
    public String getDriverId() { return driverId; }
    public String getDriverName() { return driverName; }
    public Map<String, Object> getCurrentLocation() { return currentLocation; }
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
    public void setStatus(String status) { this.status = status; }
    public void setCarrierId(String carrierId) { this.carrierId = carrierId; }
    public void setCarrierName(String carrierName) { this.carrierName = carrierName; }
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public void setCurrentLocation(Map<String, Object> currentLocation) { this.currentLocation = currentLocation; }
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
        private String status;
        private String carrierId;
        private String carrierName;
        private String driverId;
        private String driverName;
        private Map<String, Object> currentLocation;
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
        public Builder status(String status) { this.status = status; return this; }
        public Builder carrierId(String carrierId) { this.carrierId = carrierId; return this; }
        public Builder carrierName(String carrierName) { this.carrierName = carrierName; return this; }
        public Builder driverId(String driverId) { this.driverId = driverId; return this; }
        public Builder driverName(String driverName) { this.driverName = driverName; return this; }
        public Builder currentLocation(Map<String, Object> currentLocation) { this.currentLocation = currentLocation; return this; }
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

        public TrailerDocument build() {
            TrailerDocument doc = new TrailerDocument();
            doc.id = this.id;
            doc.trailerNumber = this.trailerNumber;
            doc.licensePlate = this.licensePlate;
            doc.status = this.status;
            doc.carrierId = this.carrierId;
            doc.carrierName = this.carrierName;
            doc.driverId = this.driverId;
            doc.driverName = this.driverName;
            doc.currentLocation = this.currentLocation;
            doc.currentYardLocationId = this.currentYardLocationId;
            doc.assignedDockId = this.assignedDockId;
            doc.arrivedAt = this.arrivedAt;
            doc.departedAt = this.departedAt;
            doc.dwellTimeMinutes = this.dwellTimeMinutes;
            doc.appointmentId = this.appointmentId;
            doc.cargo = this.cargo;
            doc.metadata = this.metadata;
            doc.version = this.version;
            return doc;
        }
    }
}
