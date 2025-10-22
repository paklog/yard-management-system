package com.paklog.yard.domain.event;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DockAssignedEvent extends DomainEvent {
    private String trailerId;
    private String dockId;
    private java.time.Instant assignedAt;
}
