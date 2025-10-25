package com.paklog.yard.domain.aggregate;

import com.paklog.yard.domain.valueobject.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "yard_moves")
public class YardMove {
    
    @Id
    private String id;
    
    private String trailerId;
    private String fromLocationId;
    private String toLocationId;
    
    private YardMoveStatus status;
    private String assignedJockeyId;
    
    private Instant requestedAt;
    private Instant startedAt;
    private Instant completedAt;
    
    private Integer priority;
    private String reason;
    
    @Version
    private Long version;
    
    @CreatedDate
    private Instant createdAt;
    
    @LastModifiedDate
    private Instant updatedAt;
    
    public void assign(String jockeyId) {
        this.assignedJockeyId = jockeyId;
    }
    
    public void start() {
        this.status = YardMoveStatus.IN_PROGRESS;
        this.startedAt = Instant.now();
    }
    
    public void complete() {
        this.status = YardMoveStatus.COMPLETED;
        this.completedAt = Instant.now();
    }
    
    public void cancel() {
        this.status = YardMoveStatus.CANCELLED;
    }


    // Getters
    public String getId() { return id; }
    public String getTrailerId() { return trailerId; }
    public String getFromLocationId() { return fromLocationId; }
    public String getToLocationId() { return toLocationId; }
    public YardMoveStatus getStatus() { return status; }
    public String getAssignedJockeyId() { return assignedJockeyId; }
    public Instant getRequestedAt() { return requestedAt; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getCompletedAt() { return completedAt; }
    public Integer getPriority() { return priority; }
    public String getReason() { return reason; }
    public Long getVersion() { return version; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTrailerId(String trailerId) { this.trailerId = trailerId; }
    public void setFromLocationId(String fromLocationId) { this.fromLocationId = fromLocationId; }
    public void setToLocationId(String toLocationId) { this.toLocationId = toLocationId; }
    public void setStatus(YardMoveStatus status) { this.status = status; }
    public void setAssignedJockeyId(String assignedJockeyId) { this.assignedJockeyId = assignedJockeyId; }
    public void setRequestedAt(Instant requestedAt) { this.requestedAt = requestedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
    public void setPriority(Integer priority) { this.priority = priority; }
    public void setReason(String reason) { this.reason = reason; }
    public void setVersion(Long version) { this.version = version; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
