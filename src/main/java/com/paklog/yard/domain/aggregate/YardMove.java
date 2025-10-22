package com.paklog.yard.domain.aggregate;

import com.paklog.yard.domain.valueobject.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
