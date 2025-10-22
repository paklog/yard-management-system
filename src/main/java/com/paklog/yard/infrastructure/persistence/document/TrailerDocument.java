package com.paklog.yard.infrastructure.persistence.document;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
