package com.paklog.yard.domain.event;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class LoadingCompletedEvent extends DomainEvent {
    private String trailerId;
    private java.time.Instant completedAt;
}
